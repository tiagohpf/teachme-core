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
    
    /**
     * Get a list of all users
     * @return List<User> List Users
     */
    public List<User> getAll() {
        return super.findAll();
    }

    /**
     * Remove user with a specific id
     * @param id
     * @return 1 if user was removed sucefully, -1 if an error occurred
     */
    public int removeUser(int id) {
        User user = super.find(id);
        if(user == null)
            return -1;
        super.remove(user);
        return 1;
    }

    /**
     * Update user information
     * @param id User ID
     * @param subjectId Subject ID
     * @return 1 if operation was successfully, -1 if not
     */
    public int editUser(int id, int subjectId) {
        User user = super.find(id);
        Subject subject = subjDao.find(subjectId);
        
        if (user == null || subject == null)
            return -1;
        
        user.setSubject(subject);
        subject.addUser(user);
        
        return 1;            
    }

    /**
     * Create a new user
     * @param user Entity user
     * @return id of the new user
     */
    public int createUser(User user) {
        super.create(user);
        this.em.flush();
        return user.getId();
    }

    /**
     * Get all user that live in a city
     * @param city
     * @return List of user that match our criteria
     */
    public List<User> getUserByArea(String city) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root e = cq.from(User.class);
        cq.where(cb.like(e.<String>get("city"), "%" + city + "%"));
        Query query = getEntityManager().createQuery(cq);
        return (List<User>) query.getResultList();
    }

    /**
     * Get all users that teach about a specific subject
     * @param nameSubject
     * @return List user that match our criteria
     */
    public List<User> getUsersBySubject(String nameSubject) {
        Subject subject = subjDao.getSubjectByName(nameSubject);
        if (subject == null)
            return Collections.emptyList();
        return subject.getListUser();
    }
}
