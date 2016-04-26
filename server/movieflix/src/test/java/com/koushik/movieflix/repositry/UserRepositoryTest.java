/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.repositry;

import com.koushik.movieflix.TestConfig;
import com.koushik.movieflix.entity.User;
import com.koushik.movieflix.entity.UserRating;
import com.koushik.movieflix.entity.UserRatingPK;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author koushik
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes = {TestConfig.class})
@WebAppConfiguration
public class UserRepositoryTest {

    @Mock
    private EntityManager em;
    
    @InjectMocks
    private UserRepository repo = new UserRepositryImpl();
    
    private User user;
    
    private UserRating userRating;
    
    private UserRatingPK userRatingPK;
    
    Collection<UserRating> userRatingCollection;
    
    CriteriaQuery cq;
    
    @Mock
    TypedQuery<User> query;
    
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        //setting up user
        user = new User();
        user.setId(1);
        user.setFirstname("dummy first name");
        user.setLastname("dummy last name");
        user.setEmail("dummy@gmail.com");
        user.setPassword("duumypassword");
        user.setEnables(true);
        //setting up user rating
        userRating= new UserRating();
        userRating.setComment("dummy comment");
        userRating.setRating((short)10);
        userRating.setUser(user);
        
        userRatingCollection = new ArrayList<>();
        userRatingCollection.add(userRating);
        //setting up UserRatingPK
        userRatingPK = new UserRatingPK();
        userRatingPK.setTitleId(1);
        userRatingPK.setUserId(1);
        
        userRating.setUserRatingPK(userRatingPK);
        
        user.setUserRatingCollection(userRatingCollection);
    }
    
    @Test
    public void testFindUser() {
        Mockito.when(em.find(User.class, user.getId())).thenReturn(user);
        User actual = repo.findUser(user.getId());
        Assert.assertEquals(user, actual);
    }
    
//    @Test
//    public void testFindUserEntities(){
//        Mockito.when(entityManager.getCriteriaBuilder().createQuery()).thenReturn(cq);
//        Mockito.when(entityManager.createQuery(cq)).thenReturn(query);
//       
//    }
   // @Test
    public void testFindbyEmail(){
        List<User> expected= Arrays.asList(user);  
        System.out.println("expected value:  "+ expected.get(0).getEmail());
       Mockito.when(em.createNamedQuery("User.findByEmail", User.class)).thenReturn(query);
       Mockito.when(query.getResultList()).thenReturn(expected);
//    //    Mockito.when
  //    Mockito.when(em.createNamedQuery("User.findByEmail", User.class).setParameter("email", user.getEmail()).getResultList()).thenReturn(expected);
        
    
        User actual = repo.findByEmail(user.getEmail());
        Assert.assertEquals(expected.get(0), actual);
    }
    
 //   @Test
    public void testFindbyEmailNull(){
        
        System.out.println("************ null********** "+user.getEmail());
        Mockito.when(em.createNamedQuery("User.findByEmail", User.class).setParameter("email", user.getEmail()).getResultList()).thenReturn(null);
        
        User actual = repo.findByEmail(user.getEmail());
        Assert.assertEquals(null, actual);
    }
}
