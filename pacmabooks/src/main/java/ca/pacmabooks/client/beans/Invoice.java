
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
@Named(value = "invoice")
@RequestScoped
public class Invoice implements Serializable 
{
    private int id;
    private LocalDateTime saleDate;
    private int clientId;
    private BigDecimal totalNetValueOfSale;
    private BigDecimal totalGrossSale;
    private LocalDateTime dateRemoved;
    /**
     * Creates a new instance of Invoice
     */
    public Invoice() 
    {
    }
    /*
     * @author Micahel Mcmahon
    */
    public Invoice(int id, LocalDateTime saleDate, int clientId, BigDecimal totalNetValueOfSale, BigDecimal totalGrossSale, LocalDateTime dateRemoved) {
        this.id = id;
        this.saleDate = saleDate;
        this.clientId = clientId;
        this.totalNetValueOfSale = totalNetValueOfSale;
        this.totalGrossSale = totalGrossSale;
        this.dateRemoved = dateRemoved;
    }
    /*
     * @author Micahel Mcmahon
    */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.id;
        hash = 53 * hash + Objects.hashCode(this.saleDate);
        hash = 53 * hash + this.clientId;
        hash = 53 * hash + Objects.hashCode(this.totalNetValueOfSale);
        hash = 53 * hash + Objects.hashCode(this.totalGrossSale);
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
        final Invoice other = (Invoice) obj;
        if (this.id != other.id) {
//            System.out.println("id: this->" + this.id + "  other ->" + other.id);
            return false;
        }
        if (this.clientId != other.clientId) {
//            System.out.println("clientId: this->" + this.clientId + "  other ->" + other.clientId);
            return false;
        }
        if (!Objects.equals(this.saleDate, other.saleDate)) {
//            System.out.println("saleDate: this->" + this.saleDate + "  other ->" + other.saleDate);
            return false;
        }
        if (!Objects.equals(this.totalNetValueOfSale, other.totalNetValueOfSale)) {
//            System.out.println("totalNetValueOfSale: this->" + this.totalNetValueOfSale + "  other ->" + other.totalNetValueOfSale);
            return false;
        }
        if (!Objects.equals(this.totalGrossSale, other.totalGrossSale)) {
//            System.out.println("totalGrossSale: this->" + this.totalGrossSale + "  other ->" + other.totalGrossSale);
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
    public LocalDateTime getSaleDate() {
        return saleDate;
    }
    /*
     * @author Micahel Mcmahon
    */
    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }
    /*
     * @author Micahel Mcmahon
    */
    public int getClientId() {
        return clientId;
    }
    /*
     * @author Micahel Mcmahon
    */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    /*
     * @author Micahel Mcmahon
    */
    public BigDecimal getTotalNetValueOfSale() {
        return totalNetValueOfSale;
    }
    /*
     * @author Micahel Mcmahon
    */
    public void setTotalNetValueOfSale(BigDecimal totalNetValueOfSale) {
        this.totalNetValueOfSale = totalNetValueOfSale;
    }
    /*
     * @author Micahel Mcmahon
    */
    public BigDecimal getTotalGrossSale() {
        return totalGrossSale;
    }
    /*
     * @author Micahel Mcmahon
    */
    public void setTotalGrossSale(BigDecimal totalGrossSale) {
        this.totalGrossSale = totalGrossSale;
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
