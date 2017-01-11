package ca.pacmabooks.client.beans;

import ca.pacmabooks.client.backingbeans.AuthenticationBackingBean;
import ca.pacmabooks.client.jpa.beans.util.JsfUtil;
import ca.pacmabooks.client.persistence.DaoImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Alexandre,Micahel Mcmahon
 */
@Named
@SessionScoped
public class ShoppingCart implements Serializable {

    @Inject
    private DaoImpl dao;
    private ArrayList<CartProduct> products;
    private  Tax tax;
    private HashMap<String, String> provinces;
        
    private String province;
    @Inject
    AuthenticationBackingBean client;
    
    @PostConstruct
    public void initalize()
    {
        province = "Quebec";
        provinces=  new HashMap<>();
        provinces.put("Quebec", "QC");
        provinces.put("Ontario", "ON");
        provinces.put("Nova Scotia", "NS");
        provinces.put("New Brunswick", "NB");
        provinces.put("Manitoba", "MB");
        provinces.put("British Columbia", "BC");
        provinces.put("Prince Edward Island", "PE");
        provinces.put("Saskatchewan", "SK");
        provinces.put("Alberta", "AB");
        provinces.put("Newfoundland and Labrador", "NL");
        provinces.put("Northwest Territories", "NT");
        provinces.put("Yukon", "YT");        
        provinces.put("Nunavut", "NU");
        products = new ArrayList<>();
        try
        {
            tax = (Tax) dao.findTaxByProvince(provinceToInitial());
        }
        catch(SQLException ex)
        {
        }              
    }
    /**
     * empties the cart
     * @authour Michael Mcmahon
     */
    public void emptyCart()
    {
        products.clear();
    }
    
    /**
     * The change listener when the province is changed.
     */
    public void provinceChangeListner()
    {
        try
        {
            tax = (Tax) dao.findTaxByProvince(province);
        }
        catch(SQLException se)
        {
            JsfUtil.addErrorMessage(se, "there was a problem changing the province");
        }
    }
    /**
     * returns the provinces for the dropdown
     * @authour Michael Mcmahon
     * @return 
     */
    public HashMap<String, String> getProvinces()
    {
        return provinces;
    }
    
    /**
     * Converts the full province name to 
     * the 2 character code.
     * @authour Michael Mcmahon
     * @return 
     */
    public String provinceToInitial()
    {
        return provinces.get(province);
    }
    
    /**
     * Adds the product to the cart.
     * @authour Michael Mcmahon
     * redirects the user to the shopping card page once
     * a book is added.
     * @param isbn
     * @return 
     */
    public String addProduct(String isbn)
    {
        boolean foundBook = false;
        try
        {
            for (CartProduct cp : products) 
            {
                if(cp.getBook().getIsbn().equalsIgnoreCase(isbn))
                { 
                    foundBook = true;
                    break;
                }
            }
            
            if(!foundBook)
            {
                Book b = dao.findBookByIsbn(isbn);
                CartProduct p = new CartProduct(b, 1);
                products.add(p);
            }
        }
        catch(SQLException se)
        {
            JsfUtil.addErrorMessage(se, "ther was a problem getting the book selected from the datbase");
        }
        return "shoppingCart.xhtml?faces-redirect=true";
        
    }
    
    /**
     * used to redirect the user if they try to access the checkout page
     * if they have no books in the cart
     * @authour Michael Mcmahon
     * @return 
     */
    public String redirectCheckoutUrl()
    {
        if(products.isEmpty())
            return "landing.xhtml?faces-redirect=true";
        else
            return "shoppingCart.xhtml?faces-redirect=true";
    }
    
    /**
     * used to determine if the book is in the cart already or not
     * as the user cannot add multiple copies of the same ebooks
     * @authour Michael Mcmahon
     * @param isbn
     * @return 
     */
    public boolean isBookInCart(String isbn)
    {       
        boolean foundBook = false;
        for (CartProduct cp : products) 
        {
            if(cp.getBook().getIsbn().equalsIgnoreCase(isbn))
            { 
                foundBook = true;
                break;
            }
        }
        return foundBook;
    }
    
    /**
     * Determines if the user already owns the book or not
     * as the user can not purchase multiple copies of the same
     * book.
     * @authour Michael Mcmahon
     * @param isbn
     * @return 
     */
    public boolean doesClientOwnBook(String isbn)
    {
        ArrayList<Invoice> invoices = null;
        ArrayList<InvoiceDetails> details = null;
        if(client.getClient() == null)
        {
            return false;
        }
        boolean owned = false;
        try
        {
            invoices = dao.findInvoiceByClientId(client.getClient().getId());
            for(Invoice inv : invoices)
            {
                details = dao.findDetailsByInvoiceId(inv.getId());
                for(InvoiceDetails detail : details)
                {
                    if(detail.getIsbn().equalsIgnoreCase(isbn))
                    {
                        owned = true;
                        break;
                    }
                }
            }
        }
        catch(SQLException se)
        {
            JsfUtil.addErrorMessage(se, "cannot get the users invoice to determine if the user owns the book");
        }
        return owned;
    }
    
    
    /**
     * removes the product from the shopping cart.
     * @authour Michael Mcmahon
     * @param isbn
     * @return 
     */    
    public String removeProduct(String isbn)
    {
        String url ="";
        for (CartProduct cp : products) 
        {
            if(cp.getBook().getIsbn().equalsIgnoreCase(isbn))
            {
                products.remove(cp);
                break;
            }
        }
        
        if(products.isEmpty())
            url = "landing.xhtml?faces-redirect=true";
        
        return url;
    }

    /**
     * Gets the total tax.
     * @authour Michael Mcmahon
     * @return 
     */
    public BigDecimal getTaxTotal()
    {
        BigDecimal taxTotal = new BigDecimal(0.0d);        
        taxTotal = tax.getCombinedRate();
        if(taxTotal == null)
            System.out.println("the is null");
        return taxTotal.divide(new BigDecimal(100.0d));
    }
    
    /**
     * gets the gst if it does exist.
     * @authour Michael Mcmahon
     * @return 
     */
    public BigDecimal getGst()
    {
        if(tax.getGst() != null)
            return getPriceTotal().multiply(tax.getGst().divide(new BigDecimal(100.0d))).setScale(2, RoundingMode.HALF_EVEN).stripTrailingZeros();
        else
            return new BigDecimal(0.0d).setScale(2, RoundingMode.HALF_EVEN).stripTrailingZeros();
    }
    /**
     * gets the hst.
     * @authour Michael Mcmahon
     * @return 
     */
    public BigDecimal getHst()
    {
        if(tax.getHst()!= null)
            return getPriceTotal().multiply(tax.getHst().divide(new BigDecimal(100.0d))).setScale(2, RoundingMode.HALF_EVEN).stripTrailingZeros();
        else
            return new BigDecimal(0.0d).setScale(2, RoundingMode.HALF_EVEN).stripTrailingZeros();
    }
    /**
     * gets the pst
     * @authour Michael Mcmahon
     * @return 
     */
    public BigDecimal getPst()
    {
        if(tax.getPst()!= null)
            return getPriceTotal().multiply(tax.getPst().divide(new BigDecimal(100.0d))).setScale(2, RoundingMode.HALF_EVEN).stripTrailingZeros();
        else
            return new BigDecimal(0.0d).setScale(2, RoundingMode.HALF_EVEN).stripTrailingZeros();
    }
    
    /**
     * returns the subtotal and the tax to get the total.
     * @authour Michael Mcmahon
     * @return 
     */
    public BigDecimal getTotal()
    {
            return getTax().add(getPriceTotal()).setScale(2, RoundingMode.HALF_EVEN).stripTrailingZeros();
    }
    
    /**
     * gets the total tax from the subtotal.
     * @authour Michael Mcmahon
     * @return 
     */
    public BigDecimal getTax()
    {
        return getPriceTotal().multiply(getTaxTotal()).setScale(2, RoundingMode.HALF_EVEN).stripTrailingZeros();
    }
    
    /**
     * determines if the shopping cart is empty.
     * @authour Michael Mcmahon
     * @return 
     */
    public boolean isEmpty() {
        return products.isEmpty();
    }
    
    /**
     * gets the subtotal.
     * @authour Michael Mcmahon
     * @return 
     */
    public BigDecimal getPriceTotal() {
        BigDecimal total = new BigDecimal(0.0d);
        for (CartProduct p : products) 
        {
           total = total.add(p.getPriceProduct());
        }
        return total;
    }
    
    public ArrayList<CartProduct> getProducts()
    {
        return products;
    }


    public String getProvince() 
    {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    
    /**
     * creates a copy of the cart
     * @authour Michael Mcmahon
     * @return 
     */
    public ArrayList<CartProduct> copy()
    {
        ArrayList<CartProduct> copy =  new ArrayList<>();
        for(CartProduct cp : products)
        {
            CartProduct c =  new CartProduct();
            c.setBook(cp.getBook());
            c.setQuantity(1);
            copy.add(c);
        }
        
        return copy;
    }
    /**
     * checks if the user owns the book or if the
     * book is already in the cart.
     * @authour Michael Mcmahon
     * @param isbn
     * @return 
     */
    public boolean productCheck(String isbn)
    {
        return doesClientOwnBook(isbn) || isBookInCart(isbn);
    }
}



