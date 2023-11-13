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
@RequestMapping(value = "api")
public class MemberController {

    UriBuilder uriBuilder = new UriBuilder();
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(value = "members")
    public ResponseEntity<MemberDto> createMember(@RequestBody NewMember dto) {
        MemberDto memberDto = memberService.createMember(dto);
        return ResponseEntity.created(uriBuilder.buildWithEmail(memberDto.getEmail())).build();
    }

    @GetMapping("members")
    public ResponseEntity<MemberDto> getMember(Principal principal) {
        return ResponseEntity.ok().body(memberService.getMember(principal.getName()));
    }

    @PutMapping("members")
    public ResponseEntity<MemberDto> updateMember(Principal principal,
                                                  @RequestBody MemberDto dto) {
        return ResponseEntity.ok().body(memberService.updateMember(principal, dto));
    }

    @DeleteMapping("members")
    public ResponseEntity<Object> deleteMember(Principal principal) {
        memberService.deleteMember(principal.getName());
        return ResponseEntity.noContent().build();
    }
}


/*
    @GetMapping
    public ResponseEntity<List<MemberDto>> getMembers() {
        List<MemberDto> memberDtoList = memberService.getMembers();
        return ResponseEntity.ok().body(memberDtoList);
    }
    @GetMapping(value = "/{username}")
    public ResponseEntity<MemberDto> getMember(@PathVariable("username") String username) {

        MemberDto optionalMember = memberService.getMember(username);
        return ResponseEntity.ok().body(optionalMember);
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<MemberDto> updateMember(@PathVariable("username") String username, @RequestBody MemberDto dto) {

        memberService.updateMember(username, dto);
        return ResponseEntity.noContent().buildWithEmail();
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteMember(@PathVariable("username") String username) {
        memberService.deleteMember(username);
        return ResponseEntity.noContent().buildWithEmail();
    }

    @GetMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> getMemberAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(memberService.getAuthorities(username));
    }

    @PostMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> addMemberAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            memberService.addAuthority(username, authorityName);
            return ResponseEntity.noContent().buildWithEmail();
        }
        catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteMemberAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        memberService.removeAuthority(username, authority);
        return ResponseEntity.noContent().buildWithEmail();
    }*/
