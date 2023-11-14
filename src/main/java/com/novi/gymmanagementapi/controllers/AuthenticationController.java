package com.novi.gymmanagementapi.controllers;

import com.novi.gymmanagementapi.dto.AuthenticationRequestDto;
import com.novi.gymmanagementapi.dto.AuthenticationResponseDto;
import com.novi.gymmanagementapi.services.MyCustomMemberDetailsService;
import com.novi.gymmanagementapi.utilties.JwtUtility;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final MyCustomMemberDetailsService myCustomMemberDetailsService;
    private final JwtUtility jwtUtility;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    MyCustomMemberDetailsService myCustomMemberDetailsService,
                                    JwtUtility jwtUtility) {
        this.authenticationManager = authenticationManager;
        this.myCustomMemberDetailsService = myCustomMemberDetailsService;
        this.jwtUtility = jwtUtility;
    }

    @PostMapping(value = "login")
    public ResponseEntity<?> createAuthenticationTokenForMembers(@RequestBody AuthenticationRequestDto authenticationRequestDto) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequestDto.getEmail(),
                            authenticationRequestDto.getPassword())
            );

        } catch (BadCredentialsException ex) {
            throw new Exception("Incorrect username or password", ex);
        }

        final UserDetails userDetails = myCustomMemberDetailsService.loadUserByUsername(authenticationRequestDto.getEmail());
        final String jwt = jwtUtility.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponseDto(jwt));
    }

    @GetMapping(value = "principal")
    public ResponseEntity<Object> authenticated(Principal principal) {
        return ResponseEntity.ok().body(principal);
    }
}
