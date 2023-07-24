package com.chatico.authservice.service;


import com.chatico.authservice.domain.Role;
import com.chatico.authservice.domain.UserChat;
import com.chatico.authservice.repositiry.UserChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {


    private final UserChatRepository userChatRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserChat userChat = userChatRepository.findByEmailFetchRoes(username);
        if (userChat == null) {
            throw new UsernameNotFoundException(username);
        }

        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                List<GrantedAuthority> authorities = new ArrayList<>();
                for (Role role : userChat.getRoles()) {
                    authorities.add(new SimpleGrantedAuthority(role.getName()));
                }
                return authorities;

            }

            @Override
            public String getPassword() {
                return userChat.getPassword();
            }

            @Override
            public String getUsername() {
                return userChat.getEmail();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return userChat.isEnabled();
            }
        };
    }


}
