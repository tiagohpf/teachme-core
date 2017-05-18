/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.deti.tqs.service;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pt.ua.deti.tqs.dao.SubjectDao;
import pt.ua.deti.tqs.entity.Subject;

/**
 *
 * @author tony
 */

@Path("/subject")
public class SubjectService {

    @EJB
    SubjectDao subjectDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Subject> getAll() {
        return subjectDao.getAll();
    }
    
    @POST
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String insertUser(
            @FormParam("name") String name,
            @FormParam("schoolYear") int schoolYear,
            @FormParam("price") float price) {

        Subject subject = new Subject();
        subject.setName(name);
        subject.setSchoolYear(schoolYear);
        subject.setPrice(price);

        int res;
        res = subjectDao.createSubject(subject);
        return String.valueOf(res);
    }

}
