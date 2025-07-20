package net.pranshhvoyages.journalApp.modelForKafka;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SentimentData {

    private String email;
    private String sentiment;
}
