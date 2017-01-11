package ca.pacmabooks.client.jpa.beans;

import ca.pacmabooks.client.jpa.entities.Clients;
import ca.pacmabooks.client.jpa.beans.util.JsfUtil;
import ca.pacmabooks.client.jpa.controllers.ClientsController;
import ca.pacmabooks.client.jpa.entities.Details;
import ca.pacmabooks.client.jpa.entities.Invoices;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 * Clients backing bean class with all client pages functionality.
 * @author PHILIP
 */
@Named("clientsAction")
@SessionScoped
public class ClientsAction implements Serializable {

    @EJB
    private ca.pacmabooks.client.jpa.controllers.ClientsController ejbController;
    
    private Clients current;
    private DataModel items = null;

    public ClientsAction() {}

    public Clients getCurrent() {
        if (current == null) {
            current = new Clients();
        }
        return current;
    }
    
    /**
     * Sums up all the clients non-removed purchases 
     * @return total purchases
     */
    public BigDecimal getTotalPuchases() {
        BigDecimal totalPurchases = new BigDecimal(0);
        if (current != null) {
            List<Invoices> ilist = current.getInvoicesList();
            for(Invoices i : ilist) {
                if(i.getDateRemoved() == null) {
                    List<Details> dlist = i.getDetailsList();
                    for(Details d : dlist) {
                        if(d.getDateRemoved() == null) {
                            totalPurchases.add(d.getPrice());
                        }
                    }
                }
            }
        }
        return totalPurchases;
    }

    private ClientsController getController() {
        return ejbController;
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
     * Redirects to the current Clients view page
     * @return view page redirect
     */
    public String prepareView() {
        return prepareView(current);
    }
    
    /**
     * Redirects to the view page
     * @param client to view
     * @return view page redirect
     */
    public String prepareView(Clients client) {
        try {
            current = client;
        } catch(RuntimeException e) {}
        return "View?faces-redirect=true";
    }

    /**
     * Regenerates the model and redirects to the create page
     * @return create page redirect
     */
    public String prepareCreate() {
        current = new Clients();
        return "Create?faces-redirect=true";
    }

    /**
     * Creates the client in the database.
     * Displays messages as needed.
     */
    public String create() {
        try {
            getController().create(current);
            recreateModel();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ClientsCreated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        return null;
    }

    /**
     * Redirects to the edit page
     * @param client to edit
     * @return edit page redirect
     */
    public String prepareEdit(Clients client) {
        current = client;
        return "Edit?faces-redirect=true";
    }

    /**
     * Updates the client in the database.
     * Displays messages as needed.
     * @return view page redirect
     */
    public String update() {
        try {
            getController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ClientsUpdated"));
            return "View?faces-redirect=true";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     * Deletes the client from the database
     * @param client to delete
     * @return list page link
     */
    public String destroy(Clients client) {
        current = client;
        performDestroy();
        recreateModel();
        return "List";
    }

    /**
     * Deletes the current client from the database
     * @return list page link
     */
    public String destroyAndView() {
        performDestroy();
        recreateModel();
        return "List";
    }
    
    /**
     * Deletes the client in the database.
     * Displays messages as needed.
     */
    private void performDestroy() {
        try {
            getController().delete(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ClientsDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    /**
     * Gets all the rows from the database
     * @return clients datamodel
     */
    public DataModel getItems() {
        if (items == null) {
            items = new ListDataModel(getController().findAll());
        }
        return items;
    }

    private void recreateModel() {
        items = null;
        current = null;
    }
}
