package com.novi.gymmanagementapi.controllers;

import com.novi.gymmanagementapi.dto.AuthenticationRequestDto;
import com.novi.gymmanagementapi.dto.ErrorMessageDto;
import com.novi.gymmanagementapi.dto.UserDto;
import com.novi.gymmanagementapi.services.UserService;
import com.novi.gymmanagementapi.utilties.UriBuilder;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("api/accounts")
public class AccountController {

    UriBuilder uriBuilder = new UriBuilder();

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    /* OPEN ENDPOINTS
     * A prospect can create an account
     * */

    @PostMapping
    public ResponseEntity<Object> createUserAccount(@Valid @RequestBody AuthenticationRequestDto newUser, BindingResult br) {

        if (br.getErrorCount() > 0) {
            ErrorMessageDto er = new ErrorMessageDto();
            er.setStatus("BAD_REQUEST");
            er.setField(Objects.requireNonNull(br.getFieldError()).getField());
            er.setMessage(Objects.requireNonNull(br.getFieldError()).getDefaultMessage());
            return ResponseEntity.badRequest().body(er);

        } else {
            userService.createUserAccount(newUser);
            return ResponseEntity.created(uriBuilder.buildWithEmail(newUser.getEmail())).build();

        }
    }

    @GetMapping
    public ResponseEntity<UserDto> getUserAccount(Principal principal) {
        System.out.println(principal);
        return ResponseEntity.ok().body(userService.getUserAccount(principal.getName()));
    }


}
