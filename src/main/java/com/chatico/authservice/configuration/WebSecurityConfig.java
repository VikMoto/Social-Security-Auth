package com.chatico.authservice.configuration;

import com.chatico.authservice.repositiry.UserChatRepository;
import com.chatico.authservice.service.CustomAuthenticationProvider;
import com.chatico.authservice.service.CustomUserDetailService;
import com.chatico.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    private final UserService userService;
//    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAuthenticationProvider authenticationProvider;
    private final UserChatRepository userChatRepository;


    @Autowired
    public WebSecurityConfig(UserService userService,
//                             JwtTokenProvider jwtTokenProvider,
                             CustomAuthenticationProvider authenticationProvider,
                             UserChatRepository userChatRepository) {
        this.userService = userService;
//        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationProvider = authenticationProvider;
        this.userChatRepository = userChatRepository;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailService(userChatRepository);
    }


    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws  Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(HttpMethod.POST,"/", "/api/auth/login", "/oauth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                                .loginPage("/login")
//						.failureUrl("/authentication/login?failed") // default is /login?error
//						.loginProcessingUrl("/authentication/login/process") // default is /login
                                .usernameParameter("email")
                                .passwordParameter("pass")
                                .defaultSuccessUrl("/list")
                                .permitAll()
                );
//        httpSecurity.addFilterBefore(new com.chatico.authservice.filter.JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

}
