package ca.pacmabooks.client.jpa.beans;

import ca.pacmabooks.client.jpa.entities.Rssfeeds;
import ca.pacmabooks.client.jpa.beans.util.JsfUtil;
import ca.pacmabooks.client.jpa.controllers.RssfeedsController;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 * Rssfeeds backing bean class with all rss feed pages functionality.
 * @author PHILIP
 */
@Named("rssfeedsAction")
@SessionScoped
public class RssfeedsAction implements Serializable {

    @EJB
    private ca.pacmabooks.client.jpa.controllers.RssfeedsController ejbController;

    private Rssfeeds old;
    private Rssfeeds enabled;
    private Rssfeeds current;
    private DataModel items = null;

    public RssfeedsAction() {}

    public Rssfeeds getCurrent() {
        if (current == null) {
            current = new Rssfeeds();
        }
        return current;
    }
    
    public Rssfeeds getEnabled() {
        if (enabled == null) {
            enabled = new Rssfeeds();
        }
        return enabled;
    }

    public void setEnabled(Rssfeeds enabled) {
        this.enabled = enabled;
    }

    private RssfeedsController getController() {
        return ejbController;
    }
    
    /**
     * Updates the client in the database.
     * Displays messages as needed.
     */
    public void update() {
        try {
            old.setEnabled(false);
            getController().edit(old);
            enabled.setEnabled(true);
            getController().edit(enabled);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RssfeedsUpdated"));
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
     * Creates the rss feed in the database.
     * Displays messages as needed.
     */
    public void create() {
        try {
            getController().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RssfeedsCreated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        recreateModel();
    }

    /**
     * Gets all the rows from the database.
     * Sets the list of enabled feeds as well.
     * @return clients datamodel
     */
    public DataModel getItems() {
        if (items == null) {
            List<Rssfeeds> list = getController().findAll();
            for(Rssfeeds rss : list) {
                if(rss.getEnabled()) {
                    enabled = rss;
                    old = rss;
                    break;
                }
            }
            items = new ListDataModel(list);
        }
        return items;
    }

    private void recreateModel() {
        items = null;
        current = null;
    }
    
    public Rssfeeds getRssfeeds(Integer id) {
        return ejbController.find(id);
    }

    /**
     * Converts Rssfeeds entity to a String and vice versa
     */
    @FacesConverter(forClass = Rssfeeds.class)
    public static class RssfeedsActionConverter implements Converter {

        /**
         *
         * @param facesContext
         * @param component
         * @param value
         * @return
         */
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RssfeedsAction controller = (RssfeedsAction) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rssfeedsAction");
            return controller.getRssfeeds(getKey(value));
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

        /**
         *
         * @param facesContext
         * @param component
         * @param object
         * @return
         */
        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Rssfeeds) {
                Rssfeeds o = (Rssfeeds) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Rssfeeds.class.getName());
            }
        }
    }
}
