
package ca.pacmabooks.client.backingbeans;

import ca.pacmabooks.client.beans.CartProduct;
import ca.pacmabooks.client.beans.Invoice;
import ca.pacmabooks.client.beans.InvoiceDetails;
import ca.pacmabooks.client.beans.ShoppingCart;
import ca.pacmabooks.client.business.CreditCardFactory;
import ca.pacmabooks.client.business.CreditCardValidator;
import ca.pacmabooks.client.business.controllers.util.LocaleChanger;
import ca.pacmabooks.client.enums.CreditCardType;
import ca.pacmabooks.client.jpa.beans.util.JsfUtil;
import ca.pacmabooks.client.persistence.DaoImpl;
import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * This class is used when a user is checkout and paying for 
 * books. 
 * @author Michael mcmahon
 */
@Named
@SessionScoped
public class PaymentBackBean implements Serializable
{
    @Inject
    DaoImpl dao;
    
    @Inject 
    AuthenticationBackingBean auth;
    @Inject
    LocaleChanger local;
    
    @Inject
    ShoppingCart cart;
    private int invoiceNumber;
    private ArrayList<CartProduct> products;
    
    /**
     * Retrieves the invoice number after payment is 
     * successful.
     * @author Michael mcmahon
     * @return 
     */
    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    
    private String creditCardType;
    private String[] creditCardTypes;
    private CreditCardType type;
    private boolean IsValidCreditCard = true;
    private String number;

    /**
     * gets the credit card number
     * @author Michael mcmahon
     * @return 
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the credit card number.
     * @author Michael mcmahon
     * @param number 
     */
    public void setNumber(String number) {
        this.number = number;
    }
    
    /**
     * initializes the payment bean and
     * the default credit card type of visa.
     * @author Michael mcmahon
     */
    @PostConstruct
    public void Initialize()
    {
        creditCardType = "VISA";
        type = CreditCardType.VISA;
        creditCardTypes =  new String[]{"VISA","MASTERCARD" ,"AMEX"};
    }
    
    /**
     * Used when changing the credit card type from the selection box.
     * used by the ajax call.
     * @author Michael mcmahon
     */
    public void changeCreditCardType()
    {
        type = CreditCardType.valueOf(creditCardType);
    }
    
    /**
     * Retrieves the credit card types.
     * I know i could of added a method in the enum to do this automatically
     * so that i do not have to hard code the credit card types string.
     * but since its the night before submission i do not want to screw with working
     * code.
     * @author Michael mcmahon
     * @return 
     */
    public String[] getCreditCardTypes()
    {
        return creditCardTypes;
    }
    
    /**
     * Action method that does payment processing.
     * Creates the invoice in the database.
     * returns to the invoice page once completed.
     * @author Michael mcmahon
     * @return 
     */
    public String pay()
    {
        String url = "";
        CreditCardValidator validator =  new CreditCardFactory().getCreditCardValidtor(type);
        IsValidCreditCard = validator.validate(number);
        number = "";
        if(IsValidCreditCard)
        {
            System.out.println("the credit card is valid");
            try
            {
                createInovice();
                url =  "/Invoice.xhtml?faces-redirect=true";
            }
            catch(SQLException se)
            {
                JsfUtil.addErrorMessage(se, "could not process your payment at this time.");
            }

        }
        IsValidCreditCard = false;
        products = cart.copy();
        //cart.emptyCart();
        return url;
    }
    
    /**
     * Creates the invoice with the books the user had chosen.
     * @author Michael mcmahon
     * @throws SQLException 
     */
    private void createInovice() throws SQLException
    {
        ArrayList<CartProduct> carts = cart.getProducts();
        System.out.println("the client id is: " + auth.getClient().getId());
        System.out.println("the client id is: " + auth.getClient().getFirstName());
        Invoice invoice = new Invoice();
        invoice.setClientId(auth.getClient().getId());
        invoice.setSaleDate(LocalDateTime.now());
        invoice.setTotalGrossSale(cart.getPriceTotal());
        invoice.setTotalNetValueOfSale(cart.getTotal());
        dao.createInvoice(invoice);
        invoiceNumber = invoice.getId();
        
        for(CartProduct cp : carts)
        {
            InvoiceDetails detail = new InvoiceDetails();
            detail.setBookPrice(cp.getBook().getListPrice());
            detail.setGstTax(cart.getGst());
            detail.setHstTax(cart.getHst());
            detail.setPstTax(cart.getPst());
            detail.setInvoiceId(invoice.getId());
            detail.setIsbn(cp.getBook().getIsbn());
            dao.createDetails(detail);
        }
    }
    
    /**
     * Determines if the users creditCard is valid.
     * @author Michael mcmahon
     * @return 
     */
    public boolean isValidCreditCard()
    {
        return IsValidCreditCard;
    }
    
    /**
     * returns the current credit card type
     * @author Michael mcmahon
     * @return 
     */
    public String getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
    }
    
    public LocalDateTime getDate()
    {
        return LocalDateTime.now();
    }
    
    /**
     * Gets all the products that were purchased.
     * @author Michael mcmahon
     * @return 
     */
    public ArrayList<CartProduct> getProducts()
    {
        return products;
    }
    
    /**
     * Determines if the invoice is empty or not.
     * @author Michael mcmahon
     * @return 
     */
    public boolean isInvoiceEmpty()
    {
        return products.isEmpty();
    }
    
    /**
     * Validator is used to determine if the credit card is a valid credit card
     * for the current credit card type.
     * uses the mod 10 algorithm to determine this.
     * @author Michael mcmahon
     * 
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException 
     */
    public void validateCreditCard(FacesContext context, UIComponent component, Object value) throws ValidatorException 
    { 
        //changeCreditCardType();
        System.out.println("the cc type is" + type);
        CreditCardValidator validator =  new CreditCardFactory().getCreditCardValidtor(type);
        boolean isvalidCC = validator.validate((String)value);
        
        if(!isvalidCC)
        {
            String msg = ResourceBundle.getBundle("Bundle", local.getLocale()).getString("Payment_error");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg, msg));
        }
    }
    
    /**
     * Validator to determine if the expire date is in the past. ife
     * this is true then the payment information is not valid.
     * @author Michael mcmahon
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException 
     */
    public void validateDate(FacesContext context, UIComponent component, Object value) throws ValidatorException 
    { 
        boolean isValidaDate = false;
        
        String dateString =  (String)value;
        System.out.println("the date is" + dateString);
        
        try
        {        
            LocalDate date = LocalDate.parse("01/" + dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            System.out.println("the date is" + date.toString());
            isValidaDate = date.isAfter(LocalDate.now());
            System.out.println("the date is" + date.toString());
        }
        catch(DateTimeParseException dte)
        {
            JsfUtil.addErrorMessage(dte, "the date entered is not in a valid format");
            isValidaDate = false;
        }

        if(!isValidaDate)
        {
            String msg = ResourceBundle.getBundle("Bundle", local.getLocale()).getString("Payment_date_error");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg, msg));
        }
    }        
    
}
