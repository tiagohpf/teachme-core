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

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
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
public class SearchServiceTest {
    
    private WebTarget target;
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
        
        target = client.target(URI.create(new URL(base, "api/search").toExternalForm()));
        target.register(User.class);
        
    }
    
    
    /**
     * Test of getSingle method
     */
    @Test @InSequence(1)
    public void testGetSingle() {
        target
                .queryParam("course", "TQS")
                .queryParam("district", "Porto")
                .request(MediaType.APPLICATION_JSON)
                .get(User[].class);
        User[] list = target.request(MediaType.APPLICATION_JSON).get(User[].class);
        Assert.assertNotNull(list);
    }

}
