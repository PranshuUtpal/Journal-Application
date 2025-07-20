package net.pranshhvoyages.journalApp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryQueryImplTest {

    @Autowired
    private UserRepositoryQueryImpl userRepositoryQuery;

    @Test
    public void testUserForSA(){
        userRepositoryQuery.getUserForSA();
    }
}
