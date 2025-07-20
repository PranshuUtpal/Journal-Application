package net.pranshhvoyages.journalApp.repository;

import net.pranshhvoyages.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {

    // Custom query to fetch the latest 10 journals sorted by createdDate in descending order
    @Query(value = "{}", sort = "{ 'date' : -1 }")
    List<JournalEntry> findTop10ByOrderByCreatedDateDesc();

}
