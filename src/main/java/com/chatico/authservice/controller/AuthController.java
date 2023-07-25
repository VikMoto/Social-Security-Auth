package com.chatico.authservice.controller;
//import com.chatico.authservice.dto.LoginRequestDTO;
//import com.chatico.authservice.dto.LoginResponseDTO;
//import com.chatico.authservice.service.UserService;

import com.chatico.authservice.dto.LoginRequestDTO;
import com.chatico.authservice.dto.LoginResponseDTO;
import com.chatico.authservice.service.UserAccountService;
import com.chatico.authservice.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserAccountService userAccountService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserAccountService userAccountService, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userAccountService = userAccountService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication.getName());

        UserDetails userDetails = (UserDetails) userAccountService.loadUserByUsername(loginRequest.getUsername());
        return ResponseEntity.ok(new LoginResponseDTO(jwt, userDetails.getUsername()));
    }
}
