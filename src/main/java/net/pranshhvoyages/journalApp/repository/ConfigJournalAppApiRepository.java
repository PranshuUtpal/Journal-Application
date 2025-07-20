package net.pranshhvoyages.journalApp.repository;

import net.pranshhvoyages.journalApp.entity.ConfigJournalAppApiEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppApiRepository extends MongoRepository<ConfigJournalAppApiEntity, ObjectId> {

}

