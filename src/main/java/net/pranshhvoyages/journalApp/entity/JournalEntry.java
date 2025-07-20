package net.pranshhvoyages.journalApp.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.pranshhvoyages.journalApp.enums.Sentiment;
import org.bson.codecs.ObjectIdGenerator;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Document(collection = "journal_entries")
@Getter
@Setter
public class JournalEntry {

    @Id
    private ObjectId id;

    @NonNull
    private String title;

    @NotBlank
    private String content;

    private String author;

    private LocalDateTime date;

    private Sentiment sentiment;


}
