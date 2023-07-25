//package com.chatico.authservice.configuration;//package com.example.webauth.auth;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//public class PasswordEncoderProvider {
//        @Bean
//        PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//    @Bean
//    BCryptPasswordEncoder bCryptPasswordEncoder() {
//            return  new BCryptPasswordEncoder(12);
//    }
//}
