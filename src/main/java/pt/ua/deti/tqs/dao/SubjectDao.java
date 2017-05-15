/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.deti.tqs.dao;

import java.util.List;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import static org.hibernate.annotations.common.util.impl.LoggerFactory.logger;
import pt.ua.deti.tqs.entity.Subject;
import pt.ua.deti.tqs.service.AbstractFacade;

/**
 *
 * @author tony
 */
@Stateless
public class SubjectDao extends AbstractFacade<Subject> {

    @PersistenceContext(unitName = "tqsdatabase")
    private EntityManager em;

    public SubjectDao() {
        super(Subject.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    public List<Subject> getAll() {
        return super.findAll();
    }

    public int createEntity(Subject subject) {
        super.create(subject);
        this.em.flush();
        return subject.getId();
    }
    @Transactional
    public Subject getSubjectByName(String name) {
        Subject subject = null;
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Subject> cq = cb.createQuery(Subject.class);
            Root e = cq.from(Subject.class);
            cq.where(cb.equal(e.get("name"), name));
            Query query = getEntityManager().createQuery(cq);
            subject = (Subject) query.getSingleResult();
        } catch (NoResultException ex) {
            logger(SubjectDao.class).info(ex);
        }
        return subject;
    }
}
