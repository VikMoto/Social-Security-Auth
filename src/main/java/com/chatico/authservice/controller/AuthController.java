package com.chatico.authservice.controller;

import com.chatico.authservice.dto.LoginRequestDTO;
import com.chatico.authservice.dto.LoginResponseDTO;
import com.chatico.authservice.service.CustomAuthenticationProvider;
import com.chatico.authservice.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final CustomAuthenticationProvider authenticationManager;
    private final UserAccountService userAccountService;
//    private final JwtTokenProvider jwtTokenProvider;



    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = jwtTokenProvider.generateToken(authentication.getName());

        UserDetails userDetails = (UserDetails) userAccountService.loadUserByUsername(loginRequest.getUsername());
        return ResponseEntity.ok(new LoginResponseDTO(null, userDetails.getUsername()));
    }
}
