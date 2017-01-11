
package ca.pacmabooks.client.beans;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;
import java.util.Objects;

/**
 * The bean data for a banner.
 * @author Micahel Mcmahon
 * 
 */
@Named(value = "banner")
@RequestScoped
public class Banner implements Serializable 
{

    private int id;
    private String url;
    private boolean enabled;
    /**
     * Creates a new instance of Banner
     */
    public Banner() 
    {
    }

    /*
     * @author Micahel Mcmahon
    */
    public Banner(int id, String url, boolean enabled) {
        this.id = id;
        this.url = url;
        this.enabled = enabled;
    }
    /*
     * @author Micahel Mcmahon
    */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.url);
        hash = 29 * hash + Objects.hashCode(enabled);
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
        final Banner other = (Banner) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        if (!Objects.equals(this.enabled, other.enabled)) {
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
    public String getUrl() {
        return url;
    }
    /*
     * @author Micahel Mcmahon
    */
    public void setUrl(String url) {
        this.url = url;
    }
    public boolean getEnabled() {
        return enabled;
    }
    /*
     * @author Micahel Mcmahon
    */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
}
