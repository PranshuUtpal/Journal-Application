package net.pranshhvoyages.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
public class WeatherResponse{

    private Current current;


    @Getter
    @Setter
    public class Current{
        private int temperature;

        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions;

        private int feelslike;
    }


}




