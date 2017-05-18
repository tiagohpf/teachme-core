/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.deti.tqs.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import pt.ua.deti.tqs.dao.UserDao;
import pt.ua.deti.tqs.entity.User;

/**
 *
 * @author tony
 */

@Path("/search")
public class SearchService {

    @EJB
    UserDao userDao;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> filter(
            @QueryParam("course") String course,
            @QueryParam("district") String district) {
        List<User> users = new ArrayList<>();
        if(course != null) {
            users.addAll(userDao.getUsersBySubject(course));
        }
        if(district != null) {
            users.addAll(userDao.getUserByArea(district));
        }
        return users;
    }

}
