package net.pranshhvoyages.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.pranshhvoyages.journalApp.entity.User;
import net.pranshhvoyages.journalApp.helper.Message;
import net.pranshhvoyages.journalApp.helper.UserCreationException;
import net.pranshhvoyages.journalApp.service.UserDetailsServiceImpl;
import net.pranshhvoyages.journalApp.service.UserService;
import net.pranshhvoyages.journalApp.utilis.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
//@RestController
@Controller
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "OK";
    }

//    @PostMapping("/create-user")
//    public ResponseEntity<User> createUser(@RequestBody User user){
//        try{
//            userService.saveNewUser(user);
//            return new ResponseEntity<>(user, HttpStatus.CREATED);
//        }catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }//it will works when the userService.saveNewUser(user) will be of return type void , i.e in UserService class

//    @PostMapping("/signup")
//    public ResponseEntity<User> signup(@Valid @RequestBody User user){
//        if (userService.saveNewUser(user)){
//            return new ResponseEntity<>(user, HttpStatus.CREATED);
//        }
//        else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//    }

  /*  @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @ModelAttribute("user") User user, Model model){
//        if (userService.saveNewUser(user)){
//            return new ResponseEntity<>("index", HttpStatus.CREATED);
//        }
//        else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
        System.out.println("User" + user);
        return new ResponseEntity<>("index", HttpStatus.CREATED);
    }*/

  /*  @PostMapping("/signup")
    public ModelAndView signup(@Valid @ModelAttribute("user") User user, Model model) {
        ModelAndView modelAndView = new ModelAndView();

        // Assuming userService.saveNewUser() is the check for user creation
        if (userService.saveNewUser(user)) {
            modelAndView.setViewName("index"); // The name of the Thymeleaf template (HTML page)
            modelAndView.setStatus(HttpStatus.CREATED); // Set the HTTP status
        } else {
            modelAndView.setViewName("error"); // Some error page or a redirect to signup form
            modelAndView.setStatus(HttpStatus.BAD_REQUEST); // Set HTTP status as BAD_REQUEST
        }

        //model.addAttribute("user", user); // Add user to model

        return modelAndView; // Return the ModelAndView object
    }*/

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
        try {
            if(result.hasErrors()){
                System.out.println("ERROR" + result.toString());
                model.addAttribute("user", user);
                return "index";
            }
            else if (userService.saveNewUser(user)) {
                model.addAttribute("user", new User());
                session.setAttribute("message", new Message("Successfully Registered !!", "alert-success"));
                return "index";  // Return the Thymeleaf template "index.html"
            }
        } catch (UserCreationException e) {
            e.printStackTrace();
            // Log the error status
            log.error("User registration failed: ", e);
            // Set session attribute with exception message
            session.setAttribute("message", new Message("Something went wrong: " + e.getMessage(), "alert-danger"));
            return "index"; // Redirect back to the signup page with the error
        }

        // In case something unexpected happens
        session.setAttribute("message", new Message("Something went wrong!! Need to signup again.", "alert-danger"));
        return "index"; // Return to the signup page
    }


    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody User user){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateTocken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred while create Authentication Token ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.CREATED);
        }

    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, Model model){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateTocken(userDetails.getUsername());
            model.addAttribute("jwtTokenT", jwt);
            // Assume you have a UserService to get user details from the database
            User loggedInUser = userService.findByUserName(user.getUserName());

            // Pass JWT and user details to the dashboard page
            //model.addAttribute("jwtTokenT", jwt);
            model.addAttribute("user", loggedInUser);
            model.addAttribute("username", loggedInUser.getUserName()); // Pass the username
            model.addAttribute("profileImage", loggedInUser.getProfileImage()); // Pass the profile image if available
            return "homepage";
        }catch (Exception e){
            log.error("Exception occurred while create Authentication Token ", e);
            return "index";
        }
    }

}
