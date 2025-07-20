package net.pranshhvoyages.journalApp.service;

import net.pranshhvoyages.journalApp.entity.User;
import net.pranshhvoyages.journalApp.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


//    @BeforeAll
//    void setUp(){
//        //it will execute before all test
//    }
//    @AfterAll
//    void setUP(){
//        //it will execute after all test
//    }
    @Test
    public void testFindByUserName(){
        assertEquals(4, 2 +2);
        assertNotNull(userRepository.findByUserName("pranshh"));
    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentProvider.class)
    public void testSaveNewUserByArgu(User user){
        assertTrue(userService.saveNewUser(user));
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,2,3",
            "2,3,4",
            "3,3,9"
    })
    public void test(int a, int b, int expected){
        assertEquals(expected, a+b);
    }
}
