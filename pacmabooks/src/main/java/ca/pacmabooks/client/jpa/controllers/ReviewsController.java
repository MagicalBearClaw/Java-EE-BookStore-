package ca.pacmabooks.client.jpa.controllers;

import ca.pacmabooks.client.jpa.entities.Reviews;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * JPA controller for the Reviews entity
 * @author PHILIP
 */
@Stateless
public class ReviewsController extends AbstractController<Reviews> {

    @PersistenceContext(unitName = "ca.packmabooks_client_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReviewsController() {
        super(Reviews.class);
    }
    
}
