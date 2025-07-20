package net.pranshhvoyages.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    void testSendEmail(){
        emailService.sendEmail("pranshuraj611@gmail.com",
                "Testing Java Mail Sender",
                "Hi, How are you? Jay Shree Ram! Thank You!");
    }
}
