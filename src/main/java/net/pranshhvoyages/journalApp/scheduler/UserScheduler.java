package net.pranshhvoyages.journalApp.scheduler;

import net.pranshhvoyages.journalApp.entity.JournalEntry;
import net.pranshhvoyages.journalApp.entity.User;
import net.pranshhvoyages.journalApp.enums.Sentiment;
import net.pranshhvoyages.journalApp.repository.UserRepositoryQueryImpl;
import net.pranshhvoyages.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryQueryImpl userRepositoryQuery;


    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUserAndSendSAMail(){
        List<User> userForSA = userRepositoryQuery.getUserForSA();
        for(User user : userForSA){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for(Sentiment sentiment : sentiments){
                if (sentiment != null){
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) +1);
                }
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()){
                if (entry.getValue() > maxCount){
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if(mostFrequentSentiment != null){
                String name = user.getUserName();
                emailService.sendEmail(user.getEmail(),
                        "Sentiment for last 7 days",
                        "Hi "+name+", Your sentiment for last 7 days is "+mostFrequentSentiment.toString());
            }
        }
    }
}
