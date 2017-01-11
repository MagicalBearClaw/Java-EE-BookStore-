package ca.pacmabooks.client.jpa.controllers;

import ca.pacmabooks.client.jpa.entities.Books;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *  JPA controller for the Books entity
 * @author PHILIP
 */
@Stateless
public class BooksController extends AbstractController<Books> {

    @PersistenceContext(unitName = "ca.packmabooks_client_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BooksController() {
        super(Books.class);
    }
    
    public Books findBookByIsbn(String isbn){
        TypedQuery<Books> books = em.createNamedQuery("Books.findByIsbn", Books.class).setParameter("isbn", isbn);
        return books.getSingleResult();
    }
}
