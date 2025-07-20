package net.pranshhvoyages.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.pranshhvoyages.journalApp.modelForKafka.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SentimentConsumerKafkaService {

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "weekly-sentiments", groupId = "weekly-sentiment-group")
    public void consume(SentimentData sentimentData){
        try{
            sendEmail(sentimentData);
        } catch (Exception e) {
            log.error("Exception", e);
        }

    }

    private void sendEmail(SentimentData sentimentData) {
        emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
    }
}
