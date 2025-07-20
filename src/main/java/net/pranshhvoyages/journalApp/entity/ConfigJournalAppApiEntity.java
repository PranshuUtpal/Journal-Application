package net.pranshhvoyages.journalApp.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "config_journal_app_api")
@Getter
@Setter
public class ConfigJournalAppApiEntity {

    private String key;
    private String value;

}
