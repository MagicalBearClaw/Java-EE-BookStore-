package ca.pacmabooks.client.jpa.controllers;

import ca.pacmabooks.client.jpa.entities.Rssfeeds;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * JPA controller for the Rssfeeds entity
 * @author PHILIP
 */
@Stateless
public class RssfeedsController extends AbstractController<Rssfeeds> {

    @PersistenceContext(unitName = "ca.packmabooks_client_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RssfeedsController() {
        super(Rssfeeds.class);
    }
    
}
