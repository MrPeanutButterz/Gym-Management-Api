package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.exceptions.EmailNotFoundException;
import com.novi.gymmanagementapi.models.Member;
import com.novi.gymmanagementapi.models.ProfilePicture;
import com.novi.gymmanagementapi.repositories.MemberRepository;
import com.novi.gymmanagementapi.repositories.ProfilePictureRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProfilePictureService {
    private final ProfilePictureRepository profilePictureRepository;
    private final MemberRepository memberRepository;

    public ProfilePictureService(ProfilePictureRepository doc, MemberRepository memberRepository) {
        this.profilePictureRepository = doc;
        this.memberRepository = memberRepository;
    }


    public ProfilePicture uploadProfilePicture(String email, MultipartFile file) throws IOException {
        String name = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        ProfilePicture profilePicture = new ProfilePicture();
        profilePicture.setFileName(name);
        profilePicture.setDocFile(file.getBytes());

        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.setProfilePicture(profilePicture);
            memberRepository.save(member);
        }
        return profilePicture;

    }

    public ProfilePicture downloadProfilePicture(String email) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            ProfilePicture document = profilePictureRepository.findProfilePictureById(optionalMember.get().getProfilePicture().getId());
            return document;

        } else {
            throw new EmailNotFoundException(email);
        }
    }
}