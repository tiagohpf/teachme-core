/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.deti.tqs.dao;

import java.util.List;
import java.util.logging.Logger;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pt.ua.deti.tqs.entity.Subject;
import pt.ua.deti.tqs.service.AbstractFacade;

/**
 *
 * @author tony
 */
@Stateless
public class SubjectDao extends AbstractFacade<Subject> {
    
    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());
    
    @PersistenceContext(unitName = "tqsdatabase")
    private EntityManager em;

    public SubjectDao() {
        super(Subject.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Get list of all subjects
     * @return List of Subject
     */
    public List<Subject> getAll() {
        return super.findAll();
    }

    /**
     * Create a subject 
     * @param subject
     * @return id of the new subject
     */
    public int createSubject(Subject subject) {
        super.create(subject);
        this.em.flush();
        return subject.getId();
    }

    /**
     * Get all subjects by property name
     * @param name
     * @return Subject if match was found, null if not
     */
    public Subject getSubjectByName(String name) {
        Subject subject;
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Subject> cq = cb.createQuery(Subject.class);
            Root e = cq.from(Subject.class);
            cq.where(cb.equal(e.get("name"), name));
            Query query = getEntityManager().createQuery(cq);
            subject = (Subject) query.getSingleResult();
        } catch (NoResultException ex) {
            subject = null;
            LOGGER.info(String.valueOf(ex));
        }
        return subject;
    }
}
