package com.novi.gymmanagementapi.controllers;

import com.novi.gymmanagementapi.dto.FileUploadResponseDto;
import com.novi.gymmanagementapi.models.ProfilePicture;
import com.novi.gymmanagementapi.services.ProfilePictureService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.security.Principal;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("api")
public class ProfilePictureController {

    private final ProfilePictureService profilePictureService;

    public ProfilePictureController(ProfilePictureService profilePictureService) {
        this.profilePictureService = profilePictureService;
    }

    @PostMapping("profile-picture")
    public FileUploadResponseDto uploadProfilePicture(Principal principal, @RequestParam("file") MultipartFile file) throws IOException {
        ProfilePicture profilePicture = profilePictureService.uploadProfilePicture(principal.getName(), file);
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/profile-picture/").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();
        String contentType = file.getContentType();
        return new FileUploadResponseDto(profilePicture.getFileName(), url, contentType);
    }

    @GetMapping("profile-picture")
    ResponseEntity<byte[]> downloadProfilePicture(Principal principal) {
        ProfilePicture document = profilePictureService.downloadProfilePicture(principal.getName());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + document.getFileName()).body(document.getDocFile());
    }
}