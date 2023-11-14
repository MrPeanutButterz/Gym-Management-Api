package com.novi.gymmanagementapi.controllers;

import com.novi.gymmanagementapi.dto.FullMemberDto;
import com.novi.gymmanagementapi.dto.PartialMemberDto;
import com.novi.gymmanagementapi.dto.UserDto;
import com.novi.gymmanagementapi.services.MemberService;
import com.novi.gymmanagementapi.utilties.UriBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("api")
public class MemberController {

    UriBuilder uriBuilder = new UriBuilder();
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /* OPEN ENDPOINTS */

    @PostMapping("members")
    public ResponseEntity<UserDto> createMemberAccount(@RequestBody FullMemberDto newUser) {
        UserDto newMember = memberService.createMember(newUser);
        return ResponseEntity.created(uriBuilder.buildWithEmail(newMember.getEmail())).build();
    }

    /* BELOW IS FOR AUTHENTICATED MEMBER */

    @GetMapping("members")
    public ResponseEntity<PartialMemberDto> getMemberAccountDetails(Principal principal) {
        return ResponseEntity.ok().body(memberService.getMemberAccount(principal.getName()));
    }

    @PutMapping("members")
    public ResponseEntity<PartialMemberDto> updateMemberAccount(Principal principal,
                                                                @RequestBody FullMemberDto dto) {
        return ResponseEntity.ok().body(memberService.updateMember(principal.getName(), dto));
    }

    @DeleteMapping("members")
    public ResponseEntity<Object> deleteMemberAccount(Principal principal) {
        memberService.deleteMember(principal.getName());
        return ResponseEntity.noContent().build();
    }

    /* BELOW IS FOR AUTHENTICATED ADMIN */

    @GetMapping("admin/members")
    public ResponseEntity<PartialMemberDto> getMemberAccountDetails(@RequestParam String email) {
        return ResponseEntity.ok().body(memberService.getMemberAccount(email));
    }

    @PutMapping("admin/members")
    public ResponseEntity<PartialMemberDto> updateMemberAccount(String email,
                                                                @RequestBody FullMemberDto dto) {
        return ResponseEntity.ok().body(memberService.updateMember(email, dto));
    }

    @DeleteMapping("admin/members")
    public ResponseEntity<Object> deleteMemberAccount(@RequestParam String email) {
        memberService.deleteMember(email);
        return ResponseEntity.noContent().build();
    }
}

