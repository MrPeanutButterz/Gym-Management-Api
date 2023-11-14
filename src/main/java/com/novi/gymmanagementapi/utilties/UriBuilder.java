package com.novi.gymmanagementapi.utilties;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class UriBuilder {

    public URI buildWithEmail(String email) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + email)
                .buildAndExpand(email)
                .toUri();
    }
    public URI buildWithId(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + id)
                .buildAndExpand(id)
                .toUri();
    }
}
