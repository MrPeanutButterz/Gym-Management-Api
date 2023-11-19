package com.novi.gymmanagementapi.controllers;

import com.novi.gymmanagementapi.dto.MemberDto;
import com.novi.gymmanagementapi.dto.MemberResponseDto;
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

    /* OPEN ENDPOINTS
     * A prospect can create an account
     * */

    @PostMapping("account")
    public ResponseEntity<UserDto> createMemberAccount(@RequestBody MemberDto newUser) {
        UserDto newMember = memberService.createMember(newUser);
        return ResponseEntity.created(uriBuilder.buildWithEmail(newMember.getEmail())).build();
    }

    /* BELOW IS FOR AUTHENTICATED MEMBER
     * Members can manage there accounts
     * */

    @GetMapping("members/account")
    public ResponseEntity<MemberResponseDto> getMemberAccountDetails(Principal principal) {
        return ResponseEntity.ok().body(memberService.getMemberAccount(principal.getName()));
    }

    @PutMapping("members/account")
    public ResponseEntity<MemberResponseDto> updateMemberAccount(Principal principal,
                                                                 @RequestBody MemberDto dto) {
        return ResponseEntity.ok().body(memberService.updateMember(principal.getName(), dto));
    }

    @DeleteMapping("members/account")
    public ResponseEntity<Object> deleteMemberAccount(Principal principal) {
        memberService.deleteMember(principal.getName());
        return ResponseEntity.noContent().build();
    }

    /* BELOW IS FOR AUTHENTICATED ADMIN
     * Admins can get, update or delete accounts
     * */

    @GetMapping("admin/manage-members")
    public ResponseEntity<MemberResponseDto> getMemberAccountDetails(@RequestParam String email) {
        return ResponseEntity.ok().body(memberService.getMemberAccount(email));
    }

    @PutMapping("admin/manage-members")
    public ResponseEntity<MemberResponseDto> updateMemberAccount(String email,
                                                                 @RequestBody MemberDto dto) {
        return ResponseEntity.ok().body(memberService.updateMember(email, dto));
    }

    @DeleteMapping("admin/manage-members")
    public ResponseEntity<Object> deleteMemberAccount(@RequestParam String email) {
        memberService.deleteMember(email);
        return ResponseEntity.noContent().build();
    }
}

