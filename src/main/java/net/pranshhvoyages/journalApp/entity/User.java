package net.pranshhvoyages.journalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class User {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NonNull
    @Size(min = 4 , max = 16, message = "username shoud be at least 3 character")
    private String userName;

    @NonNull
    private String password;

    private List<String> roles;


    @NotBlank(message = "email is mandatory")
    @Email(message = "email should be valid")
    private String email;

    @NotBlank(message = "name is mandatory")
    private String fullName;

    private String profileImage;
    private  boolean sentimentAnalysis;
    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();
}
