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
@RequestMapping(value = "api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<MemberDto> createMember(@RequestBody MemberDto dto) {

        String email = memberService.createMember(dto);
        memberService.addAuthority(email, "ROLE_MEMBER");

        // todo rework uri
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(email).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<MemberDto> getMember(@RequestParam String email) {
        return ResponseEntity.ok().body(memberService.getMember(email));
    }


/* WORKING FUNCTIONS ABOVE
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
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteMember(@PathVariable("username") String username) {
        memberService.deleteMember(username);
        return ResponseEntity.noContent().build();
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
            return ResponseEntity.noContent().build();
        }
        catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteMemberAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        memberService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }*/
}
