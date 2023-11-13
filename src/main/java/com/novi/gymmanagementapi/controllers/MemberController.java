package com.novi.gymmanagementapi.controllers;

import com.novi.gymmanagementapi.dto.MemberDto;
import com.novi.gymmanagementapi.dto.NewMember;
import com.novi.gymmanagementapi.utilties.UriBuilder;
import com.novi.gymmanagementapi.services.MemberService;
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

    @PostMapping(value = "members")
    public ResponseEntity<MemberDto> createMemberAccount(@RequestBody NewMember dto) {
        MemberDto memberDto = memberService.createMember(dto);
        return ResponseEntity.created(uriBuilder.buildWithEmail(memberDto.getEmail())).build();
    }

    /* BELOW IS FOR AUTHENTICATED MEMBER */

    @GetMapping("members")
    public ResponseEntity<MemberDto> getMemberAccountDetails(Principal principal) {
        return ResponseEntity.ok().body(memberService.getMemberAccount(principal.getName()));
    }

    @PutMapping("members")
    public ResponseEntity<MemberDto> updateMemberAccount(Principal principal,
                                                         @RequestBody NewMember dto) {

        // todo add updateMemberAccount
        return ResponseEntity.ok().body(memberService.updateMember(principal, dto));
    }

    @DeleteMapping("members")
    public ResponseEntity<Object> deleteMemberAccount(Principal principal) {
        memberService.deleteMember(principal.getName());
        return ResponseEntity.noContent().build();
    }

    /* BELOW IS FOR ADMIN */

    @GetMapping("admin/members")
    public ResponseEntity<MemberDto> getAccountDetails(@RequestParam String email) {
        return ResponseEntity.ok().body(memberService.getMemberAccount(email));
    }

    // todo add updateMemberAccount

    @DeleteMapping("admin/members")
    public ResponseEntity<Object> deleteMemberAccount(@RequestParam String email) {
        memberService.deleteMember(email);
        return ResponseEntity.noContent().build();
    }
}

