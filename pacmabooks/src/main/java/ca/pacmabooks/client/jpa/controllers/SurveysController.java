package ca.pacmabooks.client.jpa.controllers;

import ca.pacmabooks.client.jpa.entities.Surveys;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * JPA controller for the Surveys entity
 * @author PHILIP
 */
@Stateless
public class SurveysController extends AbstractController<Surveys> {

    @PersistenceContext(unitName = "ca.packmabooks_client_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SurveysController() {
        super(Surveys.class);
    }
    
}
