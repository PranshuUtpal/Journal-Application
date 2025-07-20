package net.pranshhvoyages.journalApp.scheduler;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserSchedulerTest {

    @Autowired
    private UserScheduler userScheduler;

    @Autowired
    private UserSchedulerUsingKafka userSchedulerUsingKafka;

    @Disabled
    @Test
    public void testFetchUsersAndSendSAMail(){
        userScheduler.fetchUserAndSendSAMail();
    }

    @Test
    public void testFetchUsersAndSendSAMailKafka(){
        userSchedulerUsingKafka.fetchUserAndSendSAMail();
    }
}
