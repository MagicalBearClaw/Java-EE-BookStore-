package ca.pacmabooks.client.jpa.beans;

import ca.pacmabooks.client.jpa.entities.Details;
import ca.pacmabooks.client.jpa.beans.util.JsfUtil;
import ca.pacmabooks.client.jpa.controllers.DetailsController;
import ca.pacmabooks.client.jpa.entities.Invoices;
import java.io.IOException;

import java.io.Serializable;
import java.util.Date;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 * Details backing bean class with all details pages functionality.
 * @author PHILIP
 */
@Named("detailsAction")
@SessionScoped
public class DetailsAction implements Serializable {

    @EJB
    private ca.pacmabooks.client.jpa.controllers.DetailsController ejbController;

    private Details current;
    private DataModel items = null;

    public DetailsAction() {}

    public Details getCurrent() {
        if (current == null) {
            current = new Details();
        }
        return current;
    }

    private DetailsController getController() {
        return ejbController;
    }

    /**
     * Regenerates the model and redirects to the list page
     * @return list page redirect
     */
    public String prepareList() {
        return "List?faces-redirect=true";
    }
    
    /**
     * Redirects to the details list page displaying only the invoice details
     * from the specified invoice.
     * @param invoice to display its details
     * @throws IOException on redirect error
     */
    public void prepareList(Invoices invoice) throws IOException {
        recreateModel();
        if(invoice != null)
            items = new ListDataModel(invoice.getDetailsList());
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("details/List.xhtml");
    }

    /**
     * Redirects to the current Details view page
     * @return view page redirect
     */
    public String prepareView() {
        return prepareView(current);
    }
    
    /**
     * Redirects to the view page
     * @param detail to view
     * @return view page redirect
     */
    public String prepareView(Details detail) {
        try {
            current = detail;
        } catch(RuntimeException e) {}
        return "View?faces-redirect=true";
    }

    /**
     * Redirects to the edit page
     * @param detail to edit
     * @return edit page redirect
     */
    public String prepareEdit(Details detail) {
        current = detail;
        return "Edit?faces-redirect=true";
    }

    /**
     * Updates the detail in the database.
     * Displays messages as needed.
     * @return view page redirect
     */
    public String update() {
        try {
            getController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DetailsUpdated"));
            return "View?faces-redirect=true";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     * Sets the details to removed with the current date
     * @param detail to remove
     * @return list page link
     */
    public String remove(Details detail) {
        current = detail;
        current.setDateRemoved(new Date());
        update();
        return "List";
    }

    /**
     * Gets all the rows from the database
     * @return details datamodel
     */
    public DataModel getItems() {
        if (items == null) {
            items = new ListDataModel(getController().findAll());
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }
}
