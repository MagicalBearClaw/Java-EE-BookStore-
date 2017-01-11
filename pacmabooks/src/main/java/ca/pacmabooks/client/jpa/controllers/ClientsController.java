package ca.pacmabooks.client.jpa.controllers;

import ca.pacmabooks.client.jpa.entities.Clients;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * JPA controller for the Clients entity
 * @author PHILIP
 */
@Stateless
public class ClientsController extends AbstractController<Clients> {

    @PersistenceContext(unitName = "ca.packmabooks_client_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClientsController() {
        super(Clients.class);
    }

    public Clients findClientByEmail(String email) {
        TypedQuery<Clients> clients = em.createNamedQuery("Clients.findByEmail", Clients.class).setParameter("email", email);
        return clients.getSingleResult();
    }
}
