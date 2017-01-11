
package ca.pacmabooks.client.backingbeans;

import ca.pacmabooks.client.beans.Book;
import ca.pacmabooks.client.beans.Invoice;
import ca.pacmabooks.client.beans.InvoiceDetails;
import ca.pacmabooks.client.jpa.beans.util.JsfUtil;
import ca.pacmabooks.client.persistence.DaoImpl;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * This class is used to keep in the Session the books the user
 * owns and is able to download when accessing the downloads page.
 * @author Micahel McMahon
 */
@Named
@SessionScoped
public class DownloadsBackingBean  implements Serializable 
{
    
    @Inject
    DaoImpl dao;
    @Inject
    AuthenticationBackingBean auth;
    
    private ArrayList<Book> downloads;
    
    private String currentFormat = "";

    public String getCurrentFormat() {
        return currentFormat;
    }

    public void setCurrentFormat(String currentFormat) {
        this.currentFormat = currentFormat;
    }
    
    @PostConstruct
    public void initialize()
    {
        downloads = new ArrayList<>();
        getClientBooks();
    }
    
    /**
     * returns all the books the user owns
     * @author Michael mcmahon
     * @return 
     */
    public ArrayList<Book> getBooks()
    {
        getClientBooks();
        return downloads;
    }
    
    /**
     * Retrieves all the books the current logged in
     * user owns. Makes sure that if an invoice is removed
     * that they will not be able to access that those books.
     * 
     * @author Michael mcmahon
     */
    private void getClientBooks()
    {
        ArrayList<Invoice> invoices = null;
        ArrayList<InvoiceDetails> details = null;
        downloads.clear();
        boolean owned = false;
        try
        {
            invoices = dao.findInvoiceByClientId(auth.getClient().getId());
            for(Invoice inv : invoices)
            {
                if(inv.getDateRemoved() == null)
                {
                    details = dao.findDetailsByInvoiceId(inv.getId());
                    for(InvoiceDetails detail : details)
                    {
                        downloads.add(dao.findBookByIsbn(detail.getIsbn()));
                    }   
                }
            }
        }
        catch(SQLException se)
        {
            JsfUtil.addErrorMessage(se, "there was an error retrieveing the downloads for the client");
        }
    }
    
    /**
     * Determines if the current logged in user
     * has any books they own.
     * @author Michael mcmahon
     * @return 
     */
    public boolean isEmpty()
    {
        getClientBooks();
        return downloads.isEmpty();
    }
    
}
