package com.novi.gymmanagementapi.controllers;

import com.novi.gymmanagementapi.dtobject.MemberDto;
import com.novi.gymmanagementapi.exceptions.BadRequestException;
import com.novi.gymmanagementapi.services.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "api/users")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<MemberDto>> getUsers() {
        List<MemberDto> memberDtoList = memberService.getUsers();
        return ResponseEntity.ok().body(memberDtoList);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<MemberDto> getUser(@PathVariable("username") String username) {

        MemberDto optionalUser = memberService.getUser(username);
        return ResponseEntity.ok().body(optionalUser);
    }

    @PostMapping
    public ResponseEntity<MemberDto> createUser(@RequestBody MemberDto dto) {

        String newUsername = memberService.createUser(dto);
        memberService.addAuthority(newUsername, "ROLE_USER");

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUsername).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<MemberDto> updateUser(@PathVariable("username") String username, @RequestBody MemberDto dto) {

        memberService.updateUser(username, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
        memberService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(memberService.getAuthorities(username));
    }

    @PostMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            memberService.addAuthority(username, authorityName);
            return ResponseEntity.noContent().build();
        }
        catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        memberService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }
}
