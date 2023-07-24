package com.chatico.authservice.configuration;

import com.chatico.authservice.service.CustomAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityFilterChainConfig {
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationToken jwtAuthenticationToken;

    public SecurityFilterChainConfig(CustomAuthenticationProvider customAuthenticationProvider, AuthenticationProvider authenticationProvider, JwtAuthenticationToken jwtAuthenticationToken) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationToken = jwtAuthenticationToken;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws  Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(HttpMethod.POST,"/", "/api/auth/login", "/oauth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagementConfigurer->
                        sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .securityContext((securityContext) ->
                        securityContext
                                .securityContextRepository()
                )
                .addFilterBefore(, UsernamePasswordAuthenticationFilter.class)
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

    @Bean
    public HttpSessionSecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }
}
