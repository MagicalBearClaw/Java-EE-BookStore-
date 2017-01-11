package ca.pacmabooks.client.jpa.controllers;

import java.util.List;
import javax.persistence.EntityManager;

/**
 * Abstract class to have all the jpa query methods that are common to all 
 * entities in the same place. Other entity controllers should extend
 * this class. 
 * @author PHILIP
 * @param <T>
 */
public abstract class AbstractController<T> {

    private Class<T> entityClass;

    public AbstractController(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    protected abstract EntityManager getEntityManager();

    /**
     * Creates the entity in the database
     * @param entity 
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }
    
    /**
     * Edits an existing entity with the new entity
     * @param entity
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    /**
     * Deletes an entity from the database
     * @param entity
     */
    public void delete(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     * Finds the entity row by the primary key
     * @param id
     * @return the entity
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    /**
     * Finds all the entity rows
     * @return all entities
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Counts the amount of entity rows
     * @return number of entities
     */
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
