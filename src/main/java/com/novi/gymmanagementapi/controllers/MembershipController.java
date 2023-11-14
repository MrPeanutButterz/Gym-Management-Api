package com.novi.gymmanagementapi.controllers;

import com.novi.gymmanagementapi.dto.MembershipDto;
import com.novi.gymmanagementapi.utilties.UriBuilder;
import com.novi.gymmanagementapi.services.MemberShipService;
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

    /* OPEN ENDPOINTS */

    @GetMapping("subscription")
    public ResponseEntity<List<MembershipDto>> getMemberships() {
        return ResponseEntity.ok(memberShipService.getMemberships());
    }

    /* BELOW IS FOR AUTHENTICATED MEMBERS */

    @PutMapping("subscription")
    public ResponseEntity<Objects> subscribeMembership(Principal principal,
                                                       @RequestParam long membershipID) {
        memberShipService.subscribe(membershipID, principal.getName());
        return ResponseEntity.created(uriBuilder.buildWithId(membershipID)).build();
    }

    @DeleteMapping("subscription")
    public ResponseEntity<Objects> unsubscribeMembership(Principal principal) {
        memberShipService.unsubscribe(principal.getName());
        return ResponseEntity.noContent().build();
    }

    /* BELOW IS FOR AUTHENTICATED TRAINERS */

    @PutMapping("staff/subscription")
    public ResponseEntity<Objects> subscribeMembership(String email,
                                                       @RequestParam long membershipID) {
        memberShipService.subscribe(membershipID, email);
        return ResponseEntity.created(uriBuilder.buildWithId(membershipID)).build();
    }

    @DeleteMapping("staff/subscription")
    public ResponseEntity<Objects> unsubscribeMembership(String email) {
        memberShipService.unsubscribe(email);
        return ResponseEntity.noContent().build();
    }

    /* BELOW IS FOR AUTHENTICATED ADMIN */

    @PostMapping("admin/subscription")
    public ResponseEntity<MembershipDto> createMembership(@Valid @RequestBody MembershipDto membershipDto) {
        MembershipDto dto = memberShipService.createMembership(membershipDto);
        return ResponseEntity.created(uriBuilder.buildWithId(dto.getId())).body(dto);
    }

    @PutMapping("admin/subscription")
    public ResponseEntity<MembershipDto> updateMembership(@RequestParam long membershipID,
                                                          @Valid @RequestBody MembershipDto membershipDto) {
        MembershipDto dto = memberShipService.updateMembership(membershipID, membershipDto);
        return ResponseEntity.created(uriBuilder.buildWithId(dto.getId())).body(dto);
    }

    @DeleteMapping("admin/subscription")
    public ResponseEntity<Objects> deleteMembership(@RequestParam long membershipID) {
        memberShipService.deleteMembership(membershipID);
        return ResponseEntity.noContent().build();
    }
}
