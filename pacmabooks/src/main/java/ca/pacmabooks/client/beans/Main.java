
package ca.pacmabooks.client.beans;

import ca.pacmabooks.client.backingbeans.CarousselBackingBean;
import ca.pacmabooks.client.jpa.beans.util.JsfUtil;
import ca.pacmabooks.client.persistence.DaoImpl;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Andrew
 *          Alexandre
 *          Michael Mcmahon
 */
@Named("main")
@SessionScoped
public class Main implements Serializable{
    
    private String randomGenre;
    
    private String searchString;
    
    private String genre;

    public String getGenre() {
        return genre;
    }
    @Inject
    DaoImpl dao;
    /** 
     * Gets a banner url from the database that is enabled.
     * @authout Michael Mcmahon
     * @param slot
     * @return 
     */
    public String getBannerAdForSlot(int slot)
    {
        String url = "";
        try
        {
            ArrayList<Banner> banners = dao.findAllBannersEnabled();
            if(banners.size() > 0)
            {
                if(banners.size() >= slot)
                {
                    url =  banners.get(slot-1).getUrl();
                }
                else
                {
                    url =  banners.get(banners.size() -1).getUrl();
                }   
            }
        }
        catch(SQLException se)
        {
            JsfUtil.addErrorMessage(se, "Could not access the abnners from the databse");
        }
        return url;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    @Inject
    private Search search;
    
    
    public String getSearchString(){
        return this.searchString;
    }
    
    public void setSearchString(String input){
        this.searchString = input;
    }
    
    public String submit(Main main){
        /*search.setMain(main);
        searchString = null;*/
        search.setInput(this.searchString);
        this.setSearchString(null);
        /*return "books?faces-redirect=true"*/
        return search.getAllBooks();
    }
    
    public String submitGenre(String genre){
        this.genre = genre;
        search.setGenre(this.genre);
        this.setGenre(null);
        return search.getAllBooks();
        /*return "books?faces-redirect=true";*/
    }

    @PostConstruct
    public void init(){
        try {
            randomGenre = dao.findUniqueGenre().get(0);
        } catch (SQLException ex) {
            Logger.getLogger(CarousselBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (randomGenre.contains("Horror")){
            randomGenre = "Horror";
        }
        else if (randomGenre.contains("Romance")){
            randomGenre = "Romance";
        }
        else if (randomGenre.contains("Mystery")){
            randomGenre = "Mystery";
        }
    }
    
    public String getRandomGenre(){
        return this.randomGenre;
    }
}
