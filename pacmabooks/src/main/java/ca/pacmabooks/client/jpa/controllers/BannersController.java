package ca.pacmabooks.client.jpa.controllers;

import ca.pacmabooks.client.jpa.entities.Banners;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * JPA controller for the Banners entity
 * @author PHILIP
 */
@Stateless
public class BannersController extends AbstractController<Banners> {

    @PersistenceContext(unitName = "ca.packmabooks_client_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BannersController() {
        super(Banners.class);
    }
    
}
