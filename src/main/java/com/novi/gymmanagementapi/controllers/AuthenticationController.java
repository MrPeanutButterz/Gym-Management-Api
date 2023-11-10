package com.novi.gymmanagementapi.controllers;

import com.novi.gymmanagementapi.dtobject.AuthenticationRequestDto;
import com.novi.gymmanagementapi.dtobject.AuthenticationResponseDto;
import com.novi.gymmanagementapi.services.MyCustomUserDetailsService;
import com.novi.gymmanagementapi.utilties.JwtUtility;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final MyCustomUserDetailsService myCustomUserDetailsService;
    private final JwtUtility jwtUtility;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    MyCustomUserDetailsService myCustomUserDetailsService,
                                    JwtUtility jwtUtility) {
        this.authenticationManager = authenticationManager;
        this.myCustomUserDetailsService = myCustomUserDetailsService;
        this.jwtUtility = jwtUtility;
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequestDto authenticationRequestDto) throws Exception {

        String username = authenticationRequestDto.getUsername();
        String password = authenticationRequestDto.getPassword();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        } catch (BadCredentialsException ex) {
            throw new Exception("Incorrect username or password", ex);
        }

        final UserDetails userDetails = myCustomUserDetailsService.loadUserByUsername(username);
        final String jwt = jwtUtility.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponseDto(jwt));
    }

    @GetMapping(value = "/authenticated")
    public ResponseEntity<Object> authenticated(Principal principal) {

        return ResponseEntity.ok().body(principal);
    }
}
