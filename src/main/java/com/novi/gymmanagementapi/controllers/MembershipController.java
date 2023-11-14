package com.novi.gymmanagementapi.controllers;

import com.novi.gymmanagementapi.dto.MembershipDto;
import com.novi.gymmanagementapi.services.MemberShipService;
import com.novi.gymmanagementapi.utilties.UriBuilder;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api")
public class MembershipController {

    UriBuilder uriBuilder = new UriBuilder();
    private final MemberShipService memberShipService;

    public MembershipController(MemberShipService memberShipService) {
        this.memberShipService = memberShipService;
    }

    /* OPEN ENDPOINTS
     * Here a prospect can get subscription information
     * */

    @GetMapping("memberships")
    public ResponseEntity<List<MembershipDto>> getMemberships() {
        return ResponseEntity.ok(memberShipService.getMemberships());
    }

    /* BELOW IS FOR AUTHENTICATED MEMBERS
     * Here a member can sign up or cancel a membership
     * */

    @PutMapping("members/subscription")
    public ResponseEntity<MembershipDto> subscribeMembership(Principal principal, @RequestParam long membershipID) {
        return ResponseEntity.ok().body(memberShipService.subscribe(membershipID, principal.getName()));
    }

    @DeleteMapping("members/subscription")
    public ResponseEntity<Objects> unsubscribeMembership(Principal principal) {
        memberShipService.unsubscribe(principal.getName());
        return ResponseEntity.noContent().build();
    }

    /* BELOW IS FOR AUTHENTICATED TRAINERS
     * Here trainers can assign of cancel a membership for a member
     * */

    @PutMapping("trainers/subscription")
    public ResponseEntity<MembershipDto> subscribeMembership(String email, @RequestParam long membershipID) {
        return ResponseEntity.ok().body(memberShipService.subscribe(membershipID, email));
    }

    @DeleteMapping("trainers/subscription")
    public ResponseEntity<Objects> unsubscribeMembership(String email) {
        memberShipService.unsubscribe(email);
        return ResponseEntity.noContent().build();
    }

    /* BELOW IS FOR AUTHENTICATED ADMIN
     * Here admins can create new memberships or update old ones or delete them
     * */

    @PostMapping("admin/subscription")
    public ResponseEntity<MembershipDto> createMembership(@Valid @RequestBody MembershipDto membershipDto) {
        MembershipDto dto = memberShipService.createMembership(membershipDto);
        return ResponseEntity.created(uriBuilder.buildWithId(dto.getId())).body(dto);
    }

    @PutMapping("admin/subscription")
    public ResponseEntity<MembershipDto> updateMembership(@RequestParam long membershipID, @Valid @RequestBody MembershipDto membershipDto) {
        return ResponseEntity.ok().body(memberShipService.updateMembership(membershipID, membershipDto));
    }

    @DeleteMapping("admin/subscription")
    public ResponseEntity<Objects> deleteMembership(@RequestParam long membershipID) {
        memberShipService.deleteMembership(membershipID);
        return ResponseEntity.noContent().build();
    }
}
