package ca.pacmabooks.client.jpa.beans;

import ca.pacmabooks.client.jpa.entities.Banners;
import ca.pacmabooks.client.jpa.beans.util.JsfUtil;
import ca.pacmabooks.client.jpa.controllers.BannersController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Banners backing bean class with all banner pages functionality.
 * @author PHILIP
 */
@Named("bannersAction")
@SessionScoped
public class BannersAction implements Serializable {

    @EJB
    private ca.pacmabooks.client.jpa.controllers.BannersController ejbController;

    private List<Banners> items = null;
    private List<Banners> enabled = null;
    private Banners current = null;

    public BannersAction() {}

    public Banners getCurrent() {
        if (current == null) {
            current = new Banners();
        }
        return current;
    }
    
    public List<Banners> getEnabled() {
        if (enabled == null) {
            getItems();
        }
        return enabled;
    }
    
    public void setEnabled(List<Banners> enabled) {
        this.enabled = enabled;
    }

    private BannersController getController() {
        return ejbController;
    }
    
    /**
     * Updates the banner in the database.
     * Displays messages as needed.
     */
    public void update() {
        try {
            for(Banners b : items) {
                b.setEnabled(false);
                getController().edit(b);
            }
            for(Banners b : enabled) {
                b.setEnabled(true);
                getController().edit(b);
            }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BannersUpdated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        recreateModel();
    }
    
    /**
     * Regenerates the model and redirects to the list page
     * @return list page redirect
     */
    public String prepareList() {
        recreateModel();
        return "List?faces-redirect=true";
    }

    /**
     * Creates the banner in the database.
     * Displays messages as needed.
     */
    public void create() {
        try {
            getController().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BannersCreated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        recreateModel();
    }

    /**
     * Gets the Banners from the database and populates the enabled list
     * @return list of banners
     */
    public List<Banners> getItems() {
        if (items == null) {
            List<Banners> list = getController().findAll();
            enabled = new ArrayList<Banners>();
            for(Banners b : list) {
                if(b.getEnabled())
                    enabled.add(b);
            }
            items = list;
        }
        return items;
    }

    private void recreateModel() {
        items = null;
        enabled = null;
        current = null;
    }

    public Banners getBanners(Integer id) {
        return getController().find(id);
    }

    /**
     * Converts Banners entity to a String and vice versa
     */
    @FacesConverter(forClass = Banners.class, value="bannersActionConverter")
    public static class BannersActionConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BannersAction controller = (BannersAction) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "bannersAction");
            return controller.getBanners(getKey(value));
        }

        Integer getKey(String value) {
            Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Banners) {
                Banners b = (Banners) object;
                return getStringKey(b.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Banners.class.getName());
            }
        }
    }
}
