package net.pranshhvoyages.journalApp.controller;

import net.pranshhvoyages.journalApp.entity.JournalEntry;
import net.pranshhvoyages.journalApp.entity.User;
import net.pranshhvoyages.journalApp.service.JournalEntryService;
import net.pranshhvoyages.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
@Controller
@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/homepage")
    public String showLoginAndSignupPage(){
        System.out.println("Hello method call");
        return "homepage";
    }
    @GetMapping("/add-journal")
    public String showAddJournalPage() {
        return "add-journal"; // This will render the add-journal.html page from the templates folder
    }

    @GetMapping("/getall")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntries(){
        List<JournalEntry> list = journalEntryService.getAll();
        if(list != null && !list.isEmpty()){
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return new  ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("/latest")
    public ResponseEntity<List<JournalEntry>> getLatestJournalEntries() {
        List<JournalEntry> list = journalEntryService.getLatestJournals(10); // Fetch latest 10 journals
        if(list != null && !list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /*@GetMapping("/myjournal")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> list = user.getJournalEntries();
        if(list != null && !list.isEmpty()){
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return new  ResponseEntity<>(HttpStatus.NOT_FOUND);

    }*/

    @GetMapping("/myjournal")
    public ResponseEntity<List<Map<String, Object>>> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> list = user.getJournalEntries();
        if (list != null && !list.isEmpty()) {
            List<Map<String, Object>> result = list.stream().map(entry -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", entry.getId().toHexString());  // Ensure id is converted to string
                map.put("title", entry.getTitle());
                map.put("content", entry.getContent());
                map.put("sentiment", entry.getSentiment());
                map.put("date", entry.getDate());
                return map;
            }).collect(Collectors.toList());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("/journalEntries/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryIdById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);

        // Find the specific journal entry by ID
        Optional<JournalEntry> journalEntry = user.getJournalEntries().stream()
                .filter(x -> x.getId().equals(myId))
                .findFirst();

        // If the entry exists, return it
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }

        // Otherwise, return not found
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if(journalEntry.isPresent()){
                boolean removed = journalEntryService.deleteByID(myId, userName);
                if (removed){
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                else{
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }

            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{myId}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (!collect.isEmpty()){
            JournalEntry old = journalEntryService.findById(myId).orElse(null);
            if(old != null){
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
                old.setSentiment(newEntry.getSentiment() != null ? newEntry.getSentiment() : old.getSentiment());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

