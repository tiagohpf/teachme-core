/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.deti.tqs.dao;

import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import pt.ua.deti.tqs.entity.User;
import pt.ua.deti.tqs.entity.Subject;
import pt.ua.deti.tqs.service.AbstractFacade;

/**
 *
 * @author tony
 */
@RunWith(Arquillian.class)
public class UserDaoTest {
    
    @Inject
    UserDao userDao;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UserDao.class.getPackage())
                .addClass(AbstractFacade.class)
                .addClass(User.class)
                .addClass(Subject.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @Test
    public void shouldCreateUsers() {
        User user = new User();
        user.setCity("Aveiro");
        user.setDescription("User 1");
        user.setName("User 1");

        int userid = userDao.createUser(user);
        assertEquals(user, userDao.find(userid));
    }
}
