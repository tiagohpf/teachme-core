/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.deti.tqs.dao;

import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pt.ua.deti.tqs.entity.Subject;
import pt.ua.deti.tqs.entity.User;
import pt.ua.deti.tqs.service.AbstractFacade;

/**
 *
 * @author tony
 */
@Stateless
public class UserDao extends AbstractFacade<User> {

    @PersistenceContext(unitName = "tqsdatabase")
    private EntityManager em;

    @EJB
    SubjectDao subjDao;
    
    public UserDao() {
        super(User.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<User> getAll() {
        return super.findAll();
    }
    public int setSubject(int id, int subjectId) {
        User user = super.find(id);
        Subject subject = subjDao.find(subjectId);
        
        if (user == null || subject == null)
            return -1;
        
        user.setSubject(subject);
        subject.addUser(user);
        
        return 1;            
    }
    public int createUser(User user) {
        super.create(user);
        this.em.flush();
        return user.getId();
    }

    public List<User> getUserByArea(String city) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root e = cq.from(User.class);
        cq.where(cb.like(e.<String>get("city"), "%" + city + "%"));
        Query query = getEntityManager().createQuery(cq);
        return (List<User>) query.getResultList();
    }
    public List<User> getUsersBySubject(String nameSubject) {
        Subject subject = subjDao.getSubjectByName(nameSubject);
        if (subject == null)
            return Collections.emptyList();
        return subject.getListUser();
    }
}
