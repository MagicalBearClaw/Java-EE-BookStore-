package ca.pacmabooks.client.jpa.beans;

import ca.pacmabooks.client.jpa.entities.Surveys;
import ca.pacmabooks.client.jpa.beans.util.JsfUtil;
import ca.pacmabooks.client.jpa.controllers.SurveysController;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 * Surveys backing bean class with all survey pages functionality.
 * @author PHILIP
 */
@Named("surveysAction")
@SessionScoped
public class SurveysAction implements Serializable {

    @EJB
    private ca.pacmabooks.client.jpa.controllers.SurveysController ejbController;

    private Surveys current;
    private DataModel items = null;

    public SurveysAction() {}

    public Surveys getCurrent() {
        if (current == null) {
            current = new Surveys();
        }
        return current;
    }

    private SurveysController getController() {
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
     * Redirects to the current Survey view page
     * @return view page redirect
     */
    public String prepareView() {
        return prepareView(current);
    }

    /**
     * Redirects to the view page
     * @param survey to view
     * @return view page redirect
     */
    public String prepareView(Surveys survey) {
        try {
            current = survey;
        } catch(RuntimeException e) {}
        return "View?faces-redirect=true";
    }

    /**
     * Regenerates the model and redirects to the create page
     * @return create page redirect
     */
    public String prepareCreate() {
        current = new Surveys();
        return "Create?faces-redirect=true";
    }

    /**
     * Creates the survey in the database.
     * Displays messages as needed.
     * @return the current page
     */
    public String create() {
        try {
            getController().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SurveysCreated"));
            recreateModel();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        return null;
    }

    /**
     * Redirects to the edit page
     * @param survey to edit
     * @return edit page redirect
     */
    public String prepareEdit(Surveys survey) {
        current = survey;
        return "Edit?faces-redirect=true";
    }

    /**
     * Updates the survey in the database.
     * Displays messages as needed.
     * @return view page redirect
     */
    public String update() {
        try {
            getController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SurveysUpdated"));
            return "View?faces-redirect=true";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     * Deletes the survey from the database
     * @param survey to delete
     * @return list page link
     */
    public String destroy(Surveys survey) {
        current = survey;
        performDestroy();
        recreateModel();
        return "List";
    }

    /**
     * Deletes the current survey from the database
     * @return list page link
     */
    public String destroyAndView() {
        performDestroy();
        recreateModel();
        return "List";
    }

    /**
     * Deletes the survey in the database.
     * Displays messages as needed.
     */
    private void performDestroy() {
        try {
            getController().delete(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SurveysDeleted"));
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
