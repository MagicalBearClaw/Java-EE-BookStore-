package ca.pacmabooks.client.business.controllers.util;

import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;

import javax.inject.Named;
// or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped;
// or import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * 
 * @author Alexandre Based on Ken's
 */

@Named
@SessionScoped
public class LocaleChanger implements Serializable {

    private Locale locale;
    
    @PostConstruct
    public void init() {
        locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    }
    
    public Locale getLocale() {
        return locale;
    }
    
    public String frenchAction() {
        this.locale = Locale.CANADA_FRENCH;
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(Locale.CANADA_FRENCH);
        
        return null;
    }

    public String englishAction() {
        this.locale = Locale.CANADA;
        FacesContext context = FacesContext.getCurrentInstance();

        context.getViewRoot().setLocale(Locale.CANADA);
        return null;
    }
}
