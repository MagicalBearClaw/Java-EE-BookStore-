package ca.pacmabooks.client.jpa.beans;

import ca.pacmabooks.client.jpa.entities.Invoices;
import ca.pacmabooks.client.jpa.beans.util.JsfUtil;
import ca.pacmabooks.client.jpa.controllers.InvoicesController;
import ca.pacmabooks.client.jpa.entities.Details;
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
 * Invoices backing bean class with all invoices pages functionality.
 * @author PHILIP
 */
@Named("invoicesAction")
@SessionScoped
public class InvoicesAction implements Serializable {

    @EJB
    private ca.pacmabooks.client.jpa.controllers.InvoicesController ejbController;
    
    @EJB
    private ca.pacmabooks.client.jpa.controllers.DetailsController detailsController;

    private Invoices current;
    private DataModel items = null;

    public InvoicesAction() {}

    public Invoices getCurrent() {
        if (current == null) {
            current = new Invoices();
        }
        return current;
    }

    private InvoicesController getController() {
        return ejbController;
    }

    /**
     * Redirects to the invoice list page
     * @throws IOException on redirect error
     */
    public void forceList() throws IOException {
        recreateModel();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("/pacmabooks/management/invoices/List.xhtml");
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
     * Redirects to the current Invoices view page
     * @return view page redirect
     */
    public String prepareView() {
        return prepareView(current);
    }
    
    /**
     * Redirects to the view page
     * @param invoice to view
     * @return view page redirect
     */
    public String prepareView(Invoices invoice) {
        try {
            current = invoice;
        } catch(RuntimeException e) {}
        return "View?faces-redirect=true";
    }

    /**
     * Redirects to the edit page
     * @param invoice to edit
     * @return edit page redirect
     */
    public String prepareEdit(Invoices invoice) {
        current = invoice;
        return "Edit?faces-redirect=true";
    }

    /**
     * Updates the invoice in the database.
     * Displays messages as needed.
     * @return view page redirect
     */
    public String update() {
        try {
            getController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("InvoicesUpdated"));
            return "View?faces-redirect=true";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    /**
     * Sets the invoice to removed with the current date. Sets all the invoice
     * details to removed as well. 
     * @param invoice to remove
     * @return list page link
     */
    public String remove(Invoices invoice) {
        if(invoice.getDateRemoved() == null) {
            current = invoice;
            current.setDateRemoved(new Date());
            update();
        }
        
        for(Details d : current.getDetailsList()) {
            if(d.getDateRemoved() == null) {
                d.setDateRemoved(new Date());
                detailsController.edit(d);
            }
        }
        recreateModel();
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
