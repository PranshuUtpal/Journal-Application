package net.pranshhvoyages.journalApp.controller;

import net.pranshhvoyages.journalApp.api.response.WeatherResponse;
import net.pranshhvoyages.journalApp.entity.User;
import net.pranshhvoyages.journalApp.repository.UserRepositoryQueryImpl;
import net.pranshhvoyages.journalApp.service.UserService;
import net.pranshhvoyages.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private UserRepositoryQueryImpl userRepositoryQuery;

   /* @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
         3List<User> list = userService.getAll();
        if(list != null && !list.isEmpty()){
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/userName/{userName}")
    public ResponseEntity<User> getUsersByUserName(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        if(user != null ){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/



    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userInDb.setFullName(user.getFullName());
        userInDb.setEmail(user.getEmail());
        userInDb.setProfileImage(user.getProfileImage());
        userInDb.setSentimentAnalysis(user.isSentimentAnalysis());
        userService.saveNewUser(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUserByName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        if(user != null ){
            userService.deleteByUserName(userName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/greeting")
    public ResponseEntity<?> greetings(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greeting ="";
        if (weatherResponse !=null){
            greeting = ", Current weather is: "+weatherResponse.getCurrent().getTemperature()+", and weather feels like " +weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi " + authentication.getName() +greeting, HttpStatus.OK);
    }

    @GetMapping("/userDetails")
    public ResponseEntity<User> userDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        if(user != null ){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sa")
    public List<User> getUserForSA(){
        return userRepositoryQuery.getUserForSA();
    }
}
//"https://api.weatherstack.com/current?access_key=<apiKey>&query=<city>"
//config_journal_app_api
//"WEATHER_API"