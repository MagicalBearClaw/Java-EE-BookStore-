
package ca.pacmabooks.client.beans;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Micahel Mcmahon
 */
@Named(value = "invoiceDetails")
@RequestScoped
public class InvoiceDetails implements Serializable 
{
    private int id;
    private int invoiceId;
    private String isbn;
    private BigDecimal pstTax;
    private BigDecimal hstTax;
    private BigDecimal gstTax;
    private BigDecimal bookPrice;
    private LocalDateTime dateRemoved;
    /**
     * Creates a new instance of InvoiceDetails
     */
    public InvoiceDetails() 
    {
    }
    /*
     * @author Micahel Mcmahon
    */
    public InvoiceDetails(int id, int invoiceId, String isbn,BigDecimal pstTax, BigDecimal hstTax, BigDecimal gstTax, BigDecimal bookPrice, LocalDateTime dateRemoved) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.isbn = isbn;
        this.pstTax = pstTax;
        this.hstTax = hstTax;
        this.gstTax = gstTax;
        this.bookPrice = bookPrice;
        this.dateRemoved = dateRemoved;
    }
    /*
     * @author Micahel Mcmahon
    */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.id;
        hash = 19 * hash + this.invoiceId;
        hash = 19 * hash + Objects.hashCode(this.isbn);
        hash = 19 * hash + Objects.hashCode(this.pstTax);
        hash = 19 * hash + Objects.hashCode(this.hstTax);
        hash = 19 * hash + Objects.hashCode(this.gstTax);
        hash = 19 * hash + Objects.hashCode(this.bookPrice);
        return hash;
    }
    /*
     * @author Micahel Mcmahon
    */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InvoiceDetails other = (InvoiceDetails) obj;
        if (this.id != other.id) {
            System.out.println("id: this->" + this.id + "  other ->" + other.id);
            return false;
        }
        if (this.invoiceId != other.invoiceId) {
            System.out.println("invoiceId: this->" + this.invoiceId + "  other ->" + other.invoiceId);
            return false;
        }
        if (!Objects.equals(this.isbn, other.isbn)) {
            System.out.println("isbn: this->" + this.isbn + "  other ->" + other.isbn);
            return false;
        }
        if (!Objects.equals(this.pstTax, other.pstTax)) {
            System.out.println("pstTax: this->" + this.pstTax + "  other ->" + other.pstTax);
            return false;
        }
        if (!Objects.equals(this.hstTax, other.hstTax)) {
            System.out.println("hstTax: this->" + this.hstTax + "  other ->" + other.hstTax);
            return false;
        }
        if (!Objects.equals(this.gstTax, other.gstTax)) {
            System.out.println("gstTax: this->" + this.gstTax + "  other ->" + other.gstTax);
            return false;
        }
        if (!Objects.equals(this.bookPrice, other.bookPrice)) {
            System.out.println("bookPrice: this->" + this.bookPrice + "  other ->" + other.bookPrice);
            return false;
        }
        return true;
    }
    /*
     * @author Micahel Mcmahon
    */
    public int getId() {
        return id;
    }
    /*
     * @author Micahel Mcmahon
    */
    public void setId(int id) {
        this.id = id;
    }
    /*
     * @author Micahel Mcmahon
    */
    public int getInvoiceId() {
        return invoiceId;
    }
    /*
     * @author Micahel Mcmahon
    */
    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }
    /*
     * @author Micahel Mcmahon
    */
    public String getIsbn() {
        return isbn;
    }
    /*
     * @author Micahel Mcmahon
    */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    /*
     * @author Micahel Mcmahon
    */
    public BigDecimal getPstTax() {
        return pstTax;
    }
    /*
     * @author Micahel Mcmahon
    */
    public void setPstTax(BigDecimal pstTax) {
        this.pstTax = pstTax;
    }
    /*
     * @author Micahel Mcmahon
    */
    public BigDecimal getHstTax() {
        return hstTax;
    }
    /*
     * @author Micahel Mcmahon
    */
    public void setHstTax(BigDecimal hstTax) {
        this.hstTax = hstTax;
    }
    /*
     * @author Micahel Mcmahon
    */
    public BigDecimal getGstTax() {
        return gstTax;
    }
    /*
     * @author Micahel Mcmahon
    */
    public void setGstTax(BigDecimal gstTax) {
        this.gstTax = gstTax;
    }
    /*
     * @author Micahel Mcmahon
    */
    public BigDecimal getBookPrice() {
        return bookPrice;
    }
    /*
     * @author Micahel Mcmahon
    */
    public void setBookPrice(BigDecimal bookPrice) {
        this.bookPrice = bookPrice;
    }
    
    /**
     * @return the dateRemoved
     * @author Christoffer Baur(1336394)
     */
    public LocalDateTime getDateRemoved() {
        return dateRemoved;
    }

    /**
     * @param dateRemoved the dateRemoved to set
     * @author Christoffer Baur(1336394)
     */
    public void setDateRemoved(LocalDateTime dateRemoved) {
        this.dateRemoved = dateRemoved;
    }
    
}
