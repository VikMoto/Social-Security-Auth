package com.chatico.authservice.dto;



import com.chatico.authservice.domain.UserChat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class UserchatRegDto {

    private String name;


    private String email;


    private String password;

    private String userPic;

    private UserChat.Gender gender;


    private List<String> roles;

    private String locale;

    private LocalDate birthday;
}
