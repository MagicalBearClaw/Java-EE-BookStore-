package ca.pacmabooks.client.beans;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Bean for a report
 *
 * @author Andrew Villavera(1330815)
 * @version 1.0
 * @since 1.8 1/30/16
 */
public class Reports {

    private String type;
    private BigDecimal total;
    private String count;
    private LocalDateTime date;

    public Reports() {
        super();
    }

    public Reports(String type, BigDecimal total, String count, LocalDateTime date) {
        this.type = type;
        this.total = total;
        this.count = count;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Reports other = (Reports) obj;
        if (this.count != other.count) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        return true;
    }
}
