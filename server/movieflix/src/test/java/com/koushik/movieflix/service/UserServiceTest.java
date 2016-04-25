/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koushik.movieflix.service;

import com.koushik.movieflix.TestConfig;
import com.koushik.movieflix.entity.User;
import com.koushik.movieflix.entity.UserRating;
import com.koushik.movieflix.entity.UserRatingPK;
import com.koushik.movieflix.repositry.UserRatingRepositry;
import com.koushik.movieflix.repositry.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
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
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserRatingRepositry userRatingRepository;

    @InjectMocks
    UserService service = new UserServiceImpl();

    private User user;

    private UserRating userRating;

    private UserRatingPK userRatingPK;

    Collection<UserRating> userRatingCollection;

    @Before
    public void setup() {
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
        userRating = new UserRating();
        userRating.setComment("dummy comment");
        userRating.setRating((short) 10);
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
    public void testIsUserExist(){
        Mockito.when(userRepository.findByEmail(user)).thenReturn(user);
        boolean actual =  service.isUserExist(user);
        Assert.assertEquals(true, actual);
    }
}
