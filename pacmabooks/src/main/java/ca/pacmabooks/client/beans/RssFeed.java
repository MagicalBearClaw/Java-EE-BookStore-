
package ca.pacmabooks.client.beans;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Micahel Mcmahon
 */
@Named(value = "rssFeed")
@RequestScoped
public class RssFeed implements Serializable 
{
    private int id;
    private String url;
    private boolean enabled;
    
    /**
     * Creates a new instance of RssFeed
     */
    public RssFeed() 
    {
    }
    /*
     * @author Micahel Mcmahon
    */
    public RssFeed(int id, String url, boolean enabled) {
        this.id = id;
        this.url = url;
        this.enabled = enabled;
    }
    /*
     * @author Micahel Mcmahon
    */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
        hash = 89 * hash + Objects.hashCode(this.url);
        hash = 89 * hash + Objects.hashCode(this.enabled);
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
        final RssFeed other = (RssFeed) obj;
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
    /*
     * @author Micahel Mcmahon
    */
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
