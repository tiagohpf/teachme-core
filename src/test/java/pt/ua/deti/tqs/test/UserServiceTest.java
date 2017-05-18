/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.deti.tqs.test;


import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import pt.ua.deti.tqs.dao.SubjectDao;
import pt.ua.deti.tqs.dao.UserDao;
import pt.ua.deti.tqs.entity.User;
import pt.ua.deti.tqs.entity.Subject;
import pt.ua.deti.tqs.service.RegistryApplication;

/**
 *
 * @author tony
 */
@RunWith(Arquillian.class)
public class UserServiceTest {
    
    private WebTarget target;
    private static final String USER_NOT_FOUND = "No users return";  
    private static final String USER_DELETE_FAIL = "Delete user failed";
    @ArquillianResource
    private URL base;
    
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClass(User.class)
                .addClass(Subject.class)
                .addClass(UserDao.class)
                .addClass(SubjectDao.class)
                .addClass(RegistryApplication.class)
                .addPackage(RegistryApplication.class.getPackage())
                .addAsResource("META-INF/persistence.xml");
    }
        
    @Before
    public void setUp() throws MalformedURLException {
        Client client = ClientBuilder.newClient();
        
        target = client.target(URI.create(new URL(base, "api/user").toExternalForm()));
        target.register(User.class);
        
    }
    
    @Test @InSequence(1)
    public void testPostAndGet() {
        User[] list = target.request(MediaType.APPLICATION_JSON).get(User[].class);
        int userCount = list.length;
        
        MultivaluedHashMap<String, String> map = new MultivaluedHashMap<>();
        map.add("name", "User Test");
        map.add("email", "email@email.com");
        map.add("dateBirth", "2016-12-12");
        target.request(MediaType.TEXT_PLAIN).post(Entity.form(map)).readEntity(String.class);
        
        map.clear();
        map.add("name", "User Test 2");
        map.add("email", "email1@email1.com");
        map.add("dateBirth", "2016-12-12");
        target.request(MediaType.TEXT_PLAIN).post(Entity.form(map)).readEntity(String.class);

        list = target.request(MediaType.APPLICATION_JSON).get(User[].class);
        Assert.assertEquals("User not inserted", userCount + 2 , list.length);
        
        assertEquals("User Test 2", list[list.length - 1].getName());
    }
    
    /**
     * Test of getSingle method
     */
    @Test @InSequence(2)
    public void testGetSingle() {
        User[] list = target.request(MediaType.APPLICATION_JSON).get(User[].class);
        Assert.assertTrue(USER_NOT_FOUND,list.length > 0);

        User user = target
                .path("{id}")
                .resolveTemplate("id", list[list.length -1].getId())
                .request(MediaType.APPLICATION_JSON)
                .get(User.class);
        assertEquals("User Test 2", user.getName());
    }
    
    /**
     * Test of edit method
     */
    @Test @InSequence(3)
    public void testEditSingle() {
        User[] list = target.request(MediaType.APPLICATION_JSON).get(User[].class);
        Assert.assertTrue(USER_NOT_FOUND,list.length > 0);
        
                
        MultivaluedHashMap<String, String> map = new MultivaluedHashMap<>();
        map.add("subjectId", "1");

        int statusCode = target
                .path("{id}")
                .resolveTemplate("id", list[0].getId())
                .request(MediaType.TEXT_PLAIN)
                .put(Entity.form(map)).getStatus();
        assertEquals("Fail on updating user", 200, statusCode);
    }
    
    
    @Test @InSequence(4)
    public void testDelete() {
        User[] list = target.request(MediaType.APPLICATION_JSON).get(User[].class);
        Assert.assertTrue(USER_NOT_FOUND,list.length > 0);
        int status = target
                .path("{id}")
                .resolveTemplate("id", list[0].getId())
                .request(MediaType.TEXT_PLAIN)
                .delete().getStatus();

        assertEquals(USER_DELETE_FAIL, 200, status);
    }
}
