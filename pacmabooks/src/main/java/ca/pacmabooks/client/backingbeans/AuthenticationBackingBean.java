
package ca.pacmabooks.client.backingbeans;

import ca.pacmabooks.client.beans.Client;
import ca.pacmabooks.client.business.controllers.util.LocaleChanger;
import ca.pacmabooks.client.jpa.beans.util.JsfUtil;
import ca.pacmabooks.client.persistence.DaoImpl;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;


/**
 *
 * @author Alexandre
 *         Michael Mcmahon
 * @since 1.0
 * @version 1.0
 * 
 * 
 * THis class is used to login, logout and keep client state
 * when a client is on the website.
 * 
 */
@Named
@SessionScoped
public class AuthenticationBackingBean implements Serializable {

    @Inject
    private DaoImpl dao;
    
    @Inject
    LocaleChanger local;
    
    private Client client;

    private String email;
    private String password;
    private boolean isManger;
    
    /**
     * @authour Michael McMahon
     * @return if the account logged in is a manager
     */
    public boolean isManager()
    {
        return isManger;
    }
    
    @PostConstruct
    public void initialize() {
        isManger = false;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * @authour Michael McMahon
     * Determines if a user is logged in or not.
     * @return 
     */
    public boolean isAuthenthicated() {
        return client != null;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @author Michael mcmahon
     *         Alexandre
     * 
     * An action that allows the user to log in.
     * it will also check if the user is a manager.
     * depending if the login is successful, unsuccessful, or 
     * if the user is method will return the appropriate url
     * to redirect too.
     * 
     * @return 
     */
    public String login() {
        
        Client temp = null;
        String url = "";
        try {
            temp = dao.findClientByEmail(email);
            if(temp == null)
                return url;
            if (temp.getPassword().equals(password)) {
                this.setClient(temp);
            }else{
                return "";
            }
            
            
            if(client.isIsManager())
            {
                url = "management/dash.xhtml?faces-redirect=true";
                
                isManger = true;
            }
            else
                url = "landing.xhtml?faces-redirect=true";
            
        } 
        catch (SQLException ex) 
        {
            JsfUtil.addErrorMessage(ex, "there was an error logging the user that could not be resolved");
        }
       
        return url;
    }
    
    /**
     * Logout a user and invalidate that session.Returns the url
     * for the home page.
     * @author Michael mcmahon
     * @return 
     */
    public String logout() 
    {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        client  =  null;
        isManger = false;
        
        return "/landing.xhtml";
    }
    
    /**
     * Validator for the email to check if the email is found in the database.
     * if it cannot find it this method will throw a validation exception
     * that will notify the user that thier credentials are invalid.
     * before logging in.
     * @authour Michael McMahon
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException 
     */
    public void validateUSerName(FacesContext context, UIComponent component, Object value) throws ValidatorException 
    { 
        Client c = null;
        try
        {
            c = dao.findClientByEmail((String)value);
        }
        catch(SQLException se)
        {
            JsfUtil.addErrorMessage(se, "There was an error accesiing the datbase to find the email specified");
        }
        if(c == null)
        {
            System.out.println("c is null");
            String msg = ResourceBundle.getBundle("Bundle", local.getLocale()).getString("Login_validator");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg, msg));
        }
    }
}
