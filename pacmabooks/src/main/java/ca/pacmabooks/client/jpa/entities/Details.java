package ca.pacmabooks.client.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PHILIP
 */
@Entity
@Table(name = "details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Details.findAll", query = "SELECT d FROM Details d"),
    @NamedQuery(name = "Details.findById", query = "SELECT d FROM Details d WHERE d.id = :id"),
    @NamedQuery(name = "Details.findByPrice", query = "SELECT d FROM Details d WHERE d.price = :price"),
    @NamedQuery(name = "Details.findByPst", query = "SELECT d FROM Details d WHERE d.pst = :pst"),
    @NamedQuery(name = "Details.findByGst", query = "SELECT d FROM Details d WHERE d.gst = :gst"),
    @NamedQuery(name = "Details.findByHst", query = "SELECT d FROM Details d WHERE d.hst = :hst"),
    @NamedQuery(name = "Details.findByInvoice", query = "SELECT d FROM Details d WHERE d.invoiceId = :invoiceId"),})
public class Details implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Min(value=0)
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private BigDecimal price;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PST")
    private BigDecimal pst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "GST")
    private BigDecimal gst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HST")
    private BigDecimal hst;
    @Column(name = "date_removed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRemoved;
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Invoices invoiceId;
    @JoinColumn(name = "isbn", referencedColumnName = "isbn")
    @ManyToOne(optional = false)
    private Books isbn;

    public Details() {
    }

    public Details(Integer id) {
        this.id = id;
    }

    public Details(Integer id, BigDecimal price, BigDecimal pst, BigDecimal gst, BigDecimal hst) {
        this.id = id;
        this.price = price;
        this.pst = pst;
        this.gst = gst;
        this.hst = hst;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPst() {
        return pst;
    }

    public void setPst(BigDecimal pst) {
        this.pst = pst;
    }

    public BigDecimal getGst() {
        return gst;
    }

    public void setGst(BigDecimal gst) {
        this.gst = gst;
    }

    public BigDecimal getHst() {
        return hst;
    }

    public void setHst(BigDecimal hst) {
        this.hst = hst;
    }

    public Invoices getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Invoices invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Books getIsbn() {
        return isbn;
    }

    public void setIsbn(Books isbn) {
        this.isbn = isbn;
    }
    
    public Date getDateRemoved() {
        return dateRemoved;
    }

    public void setDateRemoved(Date dateRemoved) {
        this.dateRemoved = dateRemoved;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Details)) {
            return false;
        }
        Details other = (Details) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ca.pacmabooks.client.jpa.enitites.Details[ id=" + id + " ]";
    }
    
}
