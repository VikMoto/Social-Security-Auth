package com.chatico.authservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;


@Entity
@Table(name = "user_chat")
@Data
@EqualsAndHashCode(of = { "id" })
@ToString(of = { "id", "userName" })
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
//public class UserChat implements UserDetails {
public class UserChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

//    @Column(nullable = false)
    private String password;

    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String AppleID;

    private String FacebookId;

    private String GoogleId;


    private String userPic;


    @Enumerated(EnumType.STRING)
    private Gender gender;


    @OneToMany(mappedBy = "userChat", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Role> roles = new HashSet<>();


//    @OneToMany(mappedBy = "userChat", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Permission> permissions = new ArrayList<>();


    private String locale;

    private LocalDate birthday;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastVisit;

    @OneToMany(
            mappedBy = "userChat",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
//    @JoinColumn(name = "group_chat_id")
    private Set<UserContacts> contacts = new TreeSet<>();
//
    public enum Gender{
        MALE, FEMALE
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserChat userChat = (UserChat) o;
        return this.id != null && Objects.equals(id, userChat.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
