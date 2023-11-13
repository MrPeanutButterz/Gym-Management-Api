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

    @GetMapping("memberships")
    public ResponseEntity<List<MembershipDto>> getMemberships() {
        return ResponseEntity
                .ok(memberShipService.getMemberships());
    }

    /* BELOW IS FOR AUTHENTICATED MEMBERS */

    @PutMapping("memberships/subscription")
    public ResponseEntity<Objects> subscribe(Principal principal,
                                             @RequestParam long membershipID) {
        memberShipService.subscribe(membershipID, principal.getName());
        return ResponseEntity
                .created(uriBuilder.buildWithId(membershipID))
                .build();
    }

    @DeleteMapping("memberships/subscription")
    public ResponseEntity<Objects> unsubscribe(Principal principal) {
        memberShipService.unsubscribe(principal.getName());
        return ResponseEntity
                .noContent()
                .build();
    }

    /* BELOW IS FOR ADMIN */

    @PostMapping("admin/memberships")
    public ResponseEntity<MembershipDto> createMembership(@Valid @RequestBody MembershipDto membershipDto) {
        MembershipDto dto = memberShipService.createMembership(membershipDto);
        return ResponseEntity
                .created(uriBuilder.buildWithId(dto.getId()))
                .body(dto);
    }

    @PutMapping("admin/memberships")
    public ResponseEntity<MembershipDto> updateMembership(@RequestParam long membershipID,
                                                          @Valid @RequestBody MembershipDto membershipDto) {
        MembershipDto dto = memberShipService.updateMembership(membershipID, membershipDto);
        return ResponseEntity
                .created(uriBuilder.buildWithId(dto.getId()))
                .body(dto);
    }

    @DeleteMapping("admin/memberships")
    public ResponseEntity<Objects> deleteMembership(@RequestParam long membershipID) {
        memberShipService.deleteMembership(membershipID);
        return ResponseEntity
                .noContent()
                .build();
    }
}
