package ca.pacmabooks.client.jpa.beans;

import ca.pacmabooks.client.jpa.entities.Reviews;
import ca.pacmabooks.client.jpa.beans.util.JsfUtil;
import ca.pacmabooks.client.jpa.controllers.ReviewsController;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 * Reviews backing bean class with all review pages functionality.
 * @author PHILIP
 */
@Named("reviewsAction")
@SessionScoped
public class ReviewsAction implements Serializable {

    @EJB
    private ca.pacmabooks.client.jpa.controllers.ReviewsController ejbController;
    
    private String numPending;
    private Reviews current;
    private DataModel items = null;

    public ReviewsAction() {}

    public Reviews getCurrent() {
        if (current == null) {
            current = new Reviews();
        }
        return current;
    }
    
    /**
     * Counts the number of pending reviews
     * @return string representation of the number of pending reviews
     */
    public String getNumPending(){
        numPending = "[0]";
        List<Reviews> list = getController().findAll();
        int i = 0;
        for(Reviews r : list) {
            if(r.getStatus().equals('P'))
                i++;
        }
        numPending = "[" + i + "]";
        return numPending;
    }

    private ReviewsController getController() {
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
     * Redirects to the current Details view page
     * @return view page redirect
     */
    public String prepareView() {
        return prepareView(current);
    }
    
    /**
     * Redirects to the view page
     * @param review to view
     * @return view page redirect
     */
    public String prepareView(Reviews review) {
        try {
            current = review;
        } catch(RuntimeException e) {}
        return "View?faces-redirect=true";
    }

    /**
     * Redirects to the edit page
     * @param review to edit
     * @return edit page redirect
     */
    public String prepareEdit(Reviews review) {
        current = review;
        return "Edit?faces-redirect=true";
    }

    /**
     * Updates the review in the database.
     * Displays messages as needed.
     * @return view page redirect
     */
    public String update() {
        try {
            getController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ReviewsUpdated"));
            return "View?faces-redirect=true";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     * Sets the reviews to removed with the current date
     * @param review to remove
     * @return list page link
     */
    public String destroy(Reviews review) {
        current = review;
        performDestroy();
        recreateModel();
        return "List";
    }

    /**
     * Deletes the review in the database.
     * Displays messages as needed.
     */
    private void performDestroy() {
        try {
            getController().delete(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ReviewsDeleted"));
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
    }
}
