package com.novi.gymmanagementapi.controllers;

import com.novi.gymmanagementapi.dto.AuthenticationRequestDto;
import com.novi.gymmanagementapi.dto.UserDto;
import com.novi.gymmanagementapi.services.UserService;
import com.novi.gymmanagementapi.utilties.FieldErrors;
import com.novi.gymmanagementapi.utilties.UriBuilder;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/accounts")
public class AccountController {

    UriBuilder uriBuilder = new UriBuilder();

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    /* OPEN ENDPOINTS
     * Anyone can create an account to become a member.
     * */

    @PostMapping
    public ResponseEntity<Object> createUserAccount(@Valid @RequestBody AuthenticationRequestDto ar,
                                                    BindingResult br) {

        if (br.getErrorCount() == 0) {
            userService.createUserAccount(ar);
            return ResponseEntity.created(uriBuilder.buildWithEmail(ar.getEmail())).build();

        } else {
            return ResponseEntity.badRequest().body(FieldErrors.catchFieldErrors(br));

        }
    }

    /* MEMBER ENDPOINTS
     * Members can get, update or delete there account
     * */

    @GetMapping
    public ResponseEntity<UserDto> getUserAccount(Principal p) {
        return ResponseEntity.ok().body(userService.getUserAccount(p.getName()));
    }

    @PutMapping
    public ResponseEntity<Object> updateUserAccount(@Valid @RequestBody UserDto u,
                                                    BindingResult br, Principal p) {

        if (br.getErrorCount() == 0) {
            userService.updateUserAccount(p.getName(), u);
            return ResponseEntity.accepted().build();

        } else {
            return ResponseEntity.badRequest().body(FieldErrors.catchFieldErrors(br));

        }
    }

    // todo delete account

}
