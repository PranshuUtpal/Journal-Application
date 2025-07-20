package net.pranshhvoyages.journalApp.service;

import net.pranshhvoyages.journalApp.api.response.WeatherResponse;
import net.pranshhvoyages.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//@Component
@Service
public class WeatherService {

    //private static final String apiKey = "e7c06361dc2cc17f37a9701a9d66440e";
    //not putting api key direct here, and put it in application.properties,
    // so that at deployement at git it shount be available to all, using below @Value annotation
    @Value("${weather.api.key}")
    private String apiKey;

   // private static final String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";
    //for above api calling we using below AppCache class to get api from database
    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired RedisService redisService;

    public WeatherResponse getWeather(String city){
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if(weatherResponse != null){
            return weatherResponse;
        }else{
            String finalAPI = appCache.appCacheList.get(AppCache.keys.WEATHER_API.toString()).replace("<city>", city).replace("<apiKey>", apiKey);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if(body != null){
                redisService.set("weather_of_" + city, body, 10L);
            }
            return body;
        }

    }
}
