package ca.pacmabooks.client.jpa.controllers;

import ca.pacmabooks.client.jpa.entities.Details;
import ca.pacmabooks.client.jpa.entities.Invoices;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * JPA controller for the Details entity
 * @author PHILIP
 */
@Stateless
public class DetailsController extends AbstractController<Details> {

    @PersistenceContext(unitName = "ca.packmabooks_client_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DetailsController() {
        super(Details.class);
    }
    
    public List<Details> findByInvoice(Invoices id) {
        return em.createNamedQuery("Details.findByInvoice")
                .setParameter("invoiceId", id)
                .getResultList();
    }
}
