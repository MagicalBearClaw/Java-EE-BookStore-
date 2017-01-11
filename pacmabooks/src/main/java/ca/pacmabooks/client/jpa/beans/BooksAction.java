package ca.pacmabooks.client.jpa.beans;

import ca.pacmabooks.client.jpa.entities.Books;
import ca.pacmabooks.client.jpa.beans.util.JsfUtil;
import ca.pacmabooks.client.jpa.controllers.BooksController;
import ca.pacmabooks.client.jpa.entities.Details;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Date;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.primefaces.model.UploadedFile;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

/**
 * Books backing bean class with all book pages functionality.
 * @author PHILIP
 */
@Named("booksAction")
@SessionScoped
public class BooksAction implements Serializable {

    @EJB
    private ca.pacmabooks.client.jpa.controllers.BooksController ejbController;
    
    private Books current = null;
    private DataModel items = null;
    private UploadedFile file = null;

    public BooksAction() {}
    
    public Books getCurrent() {
        if (current == null) {
            current = new Books();
        }
        return current;
    }
    
    /**
     * Counts the number of non-removed sales of that book
     * @return int number of sales
     */
    public int getNumSales() {
        int numSales = 0;
        if (current != null) {
            List<Details> list = current.getDetailsList();
            numSales = 0;
            for(Details d : list) {
                if(d.getDateRemoved() == null)
                    numSales++;
            }
       }
       return numSales;
    }

    private BooksController getController() {
        return ejbController;
    }
    
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
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
     * Redirects to the current Books view page
     * @return view page redirect
     */
    public String prepareView() {
        return prepareView(current);
    }
    
    /**
     * Redirects to the view page
     * @param book to view
     * @return view page redirect
     */
    public String prepareView(Books book) {
        current = book;
        return "View?faces-redirect=true";
    }

    /**
     * Regenerates the model and redirects to the create page
     * @return create page redirect
     */
    public String prepareCreate() {
        current = new Books();
        return "Create?faces-redirect=true";
    }

    /**
     * Creates the book in the database.
     * Displays messages as needed.
     * @return the current page
     */
    public String create() {
        try {
            getController().create(current);
            saveCover();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BooksCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     * Redirects to the edit page
     * @param book to edit
     * @return edit page redirect
     */
    public String prepareEdit(Books book) {
        current = book;
        return "Edit?faces-redirect=true";
    }

    /**
     * Updates the book in the database.
     * Displays messages as needed.
     * @return view page redirect
     */
    public String update() {
        try {
            getController().edit(current);
            saveCover();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BooksUpdated"));
            file = null;
            return "View?faces-redirect=true";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    /**
     * Saves the book cover in the filesystem
     */
    private void saveCover() throws IOException {
        if(file != null) {
            String filename = current.getIsbn(); 
            String extension = FilenameUtils.getExtension(file.getFileName()).toLowerCase();
            InputStream input = file.getInputstream();
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/img/covers");
            OutputStream output = new FileOutputStream(new File(path, filename + "." + extension));

            try {
                IOUtils.copy(input, output);
            } finally {
                IOUtils.closeQuietly(input);
                IOUtils.closeQuietly(output);
            }
        }
    }

    /**
     * Sets the book to removed with the current date
     * @param book to remove
     * @return list page link
     */
    public String remove(Books book) {
        current = book;
        current.setDateRemoved(new Date());
        update();
        recreateModel();
        return "List";
    }

    /**
     * Gets all the rows from the database
     * @return books datamodel
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