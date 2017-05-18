/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.deti.tqs.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pt.ua.deti.tqs.dao.UserDao;
import pt.ua.deti.tqs.entity.User;

/**
 *
 * @author tony
 */
@Path("/user")
public class UserService {

    @EJB
    UserDao userDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAll() {
        return userDao.getAll();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getById(@PathParam("id") int id) {
        return userDao.find(id);
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String delete(@PathParam("id") int id) {
        return String.valueOf(userDao.removeUser(id));
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String edit(
            @PathParam("id") int id,
            @FormParam("subjectId") int subjectId) {
        
        int res = userDao.editUser(id, subjectId);
	
        return String.valueOf(res);
    }

    @POST
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String insert(
            @FormParam("name") String name,
            @FormParam("dateBirth") String dateBirth,
            @FormParam("email") String email,
            @FormParam("description") String description,
            @FormParam("city") String city,
            @FormParam("group") boolean group,
            @FormParam("center") boolean center) {

        User user = new User();
        user.setCity(city);
        user.setName(name);
        user.setEmail(email);
        user.setDescription(description);
        user.setGroup(group);
        user.setCenter(center);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = df.parse(dateBirth);
            user.setDateBirth(date);
        } catch (ParseException ex) {
            user.setDateBirth(null);
        }
        
        int res;
        res = userDao.createUser(user);
        return String.valueOf(res);
    }

}
