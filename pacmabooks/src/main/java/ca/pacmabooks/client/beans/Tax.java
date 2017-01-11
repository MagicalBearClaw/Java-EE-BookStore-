package ca.pacmabooks.client.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.inject.Named;

/**
 * @author Philip, Mike, Christoffer
 */
@Named
public class Tax implements Serializable{
    private int id;
    private String province;
    private BigDecimal gst;
    private BigDecimal pst;
    private BigDecimal hst;
    private BigDecimal combined_rate;

    /**
     * @author Christoffer Baur 1336394
     */
    public Tax() {
        super();
    }
    
    /**
     * @author Christoffer Baur 1336394
     */
    public Tax(int id, String province, BigDecimal gst, BigDecimal pst, BigDecimal hst, BigDecimal combined_rate) {
        this.id = id;
        this.province = province;
        this.gst = gst;
        this.pst = pst;
        this.hst = hst;
        this.combined_rate = combined_rate;
    }

    /**
     * @author Christoffer Baur 1336394
     */
    public int getId() {
        return id;
    }

    /**
     * @author Christoffer Baur 1336394
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @author Christoffer Baur 1336394
     */
    public String getProvince() {
        return province;
    }

    /**
     * @author Christoffer Baur 1336394
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * @author Christoffer Baur 1336394
     */
    public BigDecimal getGst() {
        return gst;
    }

    /**
     * @author Christoffer Baur 1336394
     */
    public void setGst(BigDecimal gst) {
        this.gst = gst;
    }

    /**
     * @author Christoffer Baur 1336394
     */
    public BigDecimal getPst() {
        return pst;
    }

    /**
     * @author Christoffer Baur 1336394
     */
    public void setPst(BigDecimal pst) {
        this.pst = pst;
    }

    public BigDecimal getHst() {
        return hst;
    }

    /**
     * @author Christoffer Baur 1336394
     */
    public void setHst(BigDecimal hst) {
        this.hst = hst;
    }

    /**
     * @author Christoffer Baur 1336394
     */
    public BigDecimal getCombinedRate() {
        return combined_rate;
    }

    /**
     * @author Christoffer Baur 1336394
     */
    public void setCombinedRate(BigDecimal combined_rate) {
        this.combined_rate = combined_rate;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + this.id;
        hash = 83 * hash + Objects.hashCode(this.province);
        hash = 83 * hash + Objects.hashCode(this.gst);
        hash = 83 * hash + Objects.hashCode(this.pst);
        hash = 83 * hash + Objects.hashCode(this.combined_rate);
        return hash;
    }

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
        final Tax other = (Tax) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.province, other.province)) {
            return false;
        }
        if (!Objects.equals(this.gst, other.gst)) {
            return false;
        }
        if (!Objects.equals(this.pst, other.pst)) {
            return false;
        }
        if (!Objects.equals(this.hst, other.hst)) {
            return false;
        }
        if (!Objects.equals(this.combined_rate, other.combined_rate)) {
            return false;
        }
        return true;
    }
    
    
}
