/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.deti.tqs.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pt.ua.deti.tqs.entity.User;
import pt.ua.deti.tqs.service.AbstractFacade;

/**
 *
 * @author tony
 */
@Stateless
public class UserDao extends AbstractFacade<User>{

    @PersistenceContext(unitName = "tqsdatabase")
    private EntityManager em;

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
    
    
    
}
