/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.deti.tqs.service;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pt.ua.deti.tqs.dao.UserDao;
import pt.ua.deti.tqs.entity.User;

/**
 *
 * @author tony
 */
@Stateless
@Path("/user")
public class UserService {
    
    @EJB 
    UserDao userDao;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAll() {
        return userDao.getAll();
    }

}
