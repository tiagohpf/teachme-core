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
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
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

    @GET
    @Path("/filter/subject/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getBySubjectName(@PathParam("name") String name) {
        return userDao.getUsersBySubject(name);
    }

    @GET
    @Path("/filter/user/{area}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getByUserArea(@PathParam("area") String area) {
        return userDao.getUserByArea(area);
    }

    @PUT
    @Path("/{id}/subject")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String setSubject(
            @PathParam("id") int id,
            @FormParam("subjectId") int subjectId) {

        int res = userDao.setSubject(id, subjectId);
	
        return String.valueOf(res);
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String insertUser(
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
