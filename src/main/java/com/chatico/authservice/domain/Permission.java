package com.chatico.authservice.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "permission") // Specify the table name for the Role entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "user_chat")
//    private UserChat userChat;

    public Permission(String name) {
        this.name = name;
    }


    @JsonCreator
    public static Permission fromValue(@JsonProperty("name") String name) {
        // You may want to add validation or error handling for invalid role names
        return new Permission(name);
    }

//    @Override
//    public String getAuthority() {
//        return name;
//    }
}
