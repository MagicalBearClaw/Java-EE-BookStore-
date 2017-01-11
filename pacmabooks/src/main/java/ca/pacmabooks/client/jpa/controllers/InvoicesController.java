package ca.pacmabooks.client.jpa.controllers;

import ca.pacmabooks.client.jpa.entities.Invoices;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * JPA controller for the Invoices entity
 * @author PHILIP
 */
@Stateless
public class InvoicesController extends AbstractController<Invoices> {

    @PersistenceContext(unitName = "ca.packmabooks_client_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InvoicesController() {
        super(Invoices.class);
    }
    
}
