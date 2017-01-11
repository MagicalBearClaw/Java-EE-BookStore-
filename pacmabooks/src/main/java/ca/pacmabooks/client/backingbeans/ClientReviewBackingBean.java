/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.pacmabooks.client.backingbeans;

import ca.pacmabooks.client.persistence.DaoImpl;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Alexandre
 */
@Named
@RequestScoped
public class ClientReviewBackingBean {

    @Inject
    private DaoImpl dao;

    public String getFirst(String id) {
        try {
            return dao.findClientById(Integer.valueOf(id)).getFirstName();
        } catch (SQLException ex) {
            Logger.getLogger(ClientReviewBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getLast(String id) {
        try {
            return dao.findClientById(Integer.valueOf(id)).getLastName();
        } catch (SQLException ex) {
            Logger.getLogger(ClientReviewBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
