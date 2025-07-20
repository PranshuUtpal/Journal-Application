package net.pranshhvoyages.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.pranshhvoyages.journalApp.entity.JournalEntry;
import net.pranshhvoyages.journalApp.entity.User;
import net.pranshhvoyages.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//@Component
@Service
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try{
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            journalEntry.setAuthor(user.getFullName());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occurred while saving the journal entry.", e);
        }

    }

    //Overloaded saveEntry Method
    public void saveEntry(JournalEntry journalEntry){
       journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public List<JournalEntry> getLatestJournals(int limit){
        return journalEntryRepository.findTop10ByOrderByCreatedDateDesc();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteByID(ObjectId id, String userName){
        boolean removed = false;
        try {
            User user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            log.error("Error ", e);
            throw new RuntimeException("An error occurred while deleting the entry.",e);
        }
        return removed;
    }
}
