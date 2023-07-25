package com.chatico.authservice.dto;



import com.chatico.authservice.domain.Role;
import com.chatico.authservice.domain.UserChat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class UserchatRegDto {

    private String username;
    private String email;
    private String password;
    private String userPic;
    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
    private Boolean isEnabled;

    private Set<Role> roles;
    private UserChat.Gender gender;
    private String locale;
    private LocalDate birthday;
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}
