package net.pranshhvoyages.journalApp.service;

import net.pranshhvoyages.journalApp.entity.User;
import net.pranshhvoyages.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

//@SpringBootTest
public class UserDetailsServiceImplTest {

    //@Autowired
    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImp;

    //@MockBean
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUsernameTest(){
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("utpal1").password("utpal1").roles(new ArrayList<>()).build());
        UserDetails user = userDetailsServiceImp.loadUserByUsername("utpal1");
        Assertions.assertNotNull(user);
    }
}

