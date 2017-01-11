/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.pacmabooks.client.backingbeans;

import ca.pacmabooks.client.beans.RssFeed;
import ca.pacmabooks.client.persistence.DaoImpl;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Alexandre, Philip
 */
@Named
@RequestScoped
public class RssBackingBean implements Serializable{
    
    private RssFeed rss;
    
    @Inject
    private DaoImpl dao;

    public RssFeed getRss() {
        return rss;
    }
    
    @PostConstruct
    public void init(){
        ArrayList<RssFeed> feeds = null;
        try {
            feeds = dao.findAllRssFeedsEnabled();
        } catch (SQLException ex) {
            Logger.getLogger(RssBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (feeds != null){
            this.rss = feeds.get(0);
        }
    }
    
}
