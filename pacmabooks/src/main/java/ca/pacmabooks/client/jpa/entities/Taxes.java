package ca.pacmabooks.client.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PHILIP
 */
@Entity
@Table(name = "taxes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Taxes.findAll", query = "SELECT t FROM Taxes t"),
    @NamedQuery(name = "Taxes.findById", query = "SELECT t FROM Taxes t WHERE t.id = :id"),
    @NamedQuery(name = "Taxes.findByProvince", query = "SELECT t FROM Taxes t WHERE t.province = :province"),
    @NamedQuery(name = "Taxes.findByGst", query = "SELECT t FROM Taxes t WHERE t.gst = :gst"),
    @NamedQuery(name = "Taxes.findByPst", query = "SELECT t FROM Taxes t WHERE t.pst = :pst"),
    @NamedQuery(name = "Taxes.findByHst", query = "SELECT t FROM Taxes t WHERE t.hst = :hst"),
    @NamedQuery(name = "Taxes.findByCombinedRate", query = "SELECT t FROM Taxes t WHERE t.combinedRate = :combinedRate")})
public class Taxes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "province")
    private String province;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "gst")
    private BigDecimal gst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pst")
    private BigDecimal pst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hst")
    private BigDecimal hst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "combined_rate")
    private BigDecimal combinedRate;

    public Taxes() {
    }

    public Taxes(Integer id) {
        this.id = id;
    }

    public Taxes(Integer id, String province, BigDecimal gst, BigDecimal pst, BigDecimal hst, BigDecimal combinedRate) {
        this.id = id;
        this.province = province;
        this.gst = gst;
        this.pst = pst;
        this.hst = hst;
        this.combinedRate = combinedRate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public BigDecimal getGst() {
        return gst;
    }

    public void setGst(BigDecimal gst) {
        this.gst = gst;
    }

    public BigDecimal getPst() {
        return pst;
    }

    public void setPst(BigDecimal pst) {
        this.pst = pst;
    }

    public BigDecimal getHst() {
        return hst;
    }

    public void setHst(BigDecimal hst) {
        this.hst = hst;
    }

    public BigDecimal getCombinedRate() {
        return combinedRate;
    }

    public void setCombinedRate(BigDecimal combinedRate) {
        this.combinedRate = combinedRate;
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
        if (!(object instanceof Taxes)) {
            return false;
        }
        Taxes other = (Taxes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ca.pacmabooks.client.jpa.enitites.Taxes[ id=" + id + " ]";
    }
    
}
