
package ca.pacmabooks.client.business.controllers;

import ca.pacmabooks.client.beans.Client;
import ca.pacmabooks.client.business.controllers.util.LocaleChanger;
import ca.pacmabooks.client.jpa.beans.util.JsfUtil;
import ca.pacmabooks.client.persistence.DaoImpl;
import java.io.Serializable;
import java.sql.SQLException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Used to register a user so that they can purchase 
 * a book
 * @author Michael McMahon (1330815)
 */
@Named("registerController")
@RequestScoped
public class RegisterController implements Serializable
{
    
    private Client client;
    @Inject
    DaoImpl dao;
    
    @Inject
    LocaleChanger local;
    
    public Client getClient()
    {
        if(client == null)
            client =  new Client();
        
        return client;
        
    }
    public void setClient(Client client) {
        this.client = client;
    }
    
    /**
     * Determines if the email is already taken, as emails
     * are unique.
     * @authour Michael Mcmahon
     * @return 
     */
    public boolean isEmailTaken() 
    {
        boolean valid = true;
        if(client == null)
            return false;
        String email = client.getEmail();
        System.out.println("email is for client" + client.getEmail());
        try
        {
            if (email != null) 
            {
                if (dao.findClientByEmail(client.getEmail()) == null) 
                {
                    valid = false;
                } 
            }
        }
        catch(SQLException se)
        {
            JsfUtil.addErrorMessage(se, "there was an error checking if the email is already taken");
        }
        return valid;
    }
    
    /**
     * Action method to register a client
     * return the user to the main page if it was successful.
     * @authour Michael Mcmahon
     * @return 
     */
    public String registerClient()
    {
        String url = "";
        System.out.println("registering client");
        if(!isEmailTaken())
        {
            try
            {   
                dao.createClient(client);
                url = "landing.xhtml?faces-redirect=true";
            }
            catch(SQLException se)
            {
                JsfUtil.addErrorMessage(se, "Could not register a client into the databse");
            }
        }
        return url;       
    }
       
}