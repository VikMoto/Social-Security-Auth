package com.chatico.authservice.service;

import com.chatico.authservice.repositiry.UserChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

;


@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailService userDetailService;
    private final PasswordEncoder passwordEncoder;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public CustomAuthenticationProvider(CustomUserDetailService userDetailService,
                                        PasswordEncoder passwordEncoder) {
        this.userDetailService = userDetailService;    
        this.passwordEncoder = passwordEncoder;
}



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("authentication.getPrincipal().getClass() = " + authentication.getPrincipal().getClass());
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails securityUser= userDetailService.loadUserByUsername(username);
        return checkPassword(securityUser, password);
    }


    private UsernamePasswordAuthenticationToken checkPassword(UserDetails user, String rowPassword) {

        if(passwordEncoder.matches(rowPassword, user.getPassword())) {
            UserDetails innerUser = User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities(user.getAuthorities())
                    .build();

            return new UsernamePasswordAuthenticationToken(innerUser, user.getPassword(), user.getAuthorities());
        } else {
            throw new BadCredentialsException("Invalid password");
        }

    }


    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }


}
