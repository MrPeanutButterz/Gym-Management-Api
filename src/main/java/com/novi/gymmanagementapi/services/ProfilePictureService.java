package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.exceptions.EmailNotFoundException;
import com.novi.gymmanagementapi.exceptions.RecordNotFoundException;
import com.novi.gymmanagementapi.models.ProfilePicture;
import com.novi.gymmanagementapi.repositories.UserRepository;
import com.novi.gymmanagementapi.repositories.ProfilePictureRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProfilePictureService {
    private final ProfilePictureRepository profilePictureRepository;
    private final UserRepository userRepository;

    public ProfilePictureService(ProfilePictureRepository doc, UserRepository userRepository) {
        this.profilePictureRepository = doc;
        this.userRepository = userRepository;
    }

    public ProfilePicture uploadProfilePicture(String email, MultipartFile file) throws IOException {
/*        String name = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        ProfilePicture profilePicture = new ProfilePicture();
        profilePicture.setFileName(name);
        profilePicture.setDocFile(file.getBytes());

        Optional<Member> optionalMember = userRepository.findById(email);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.setProfilePicture(profilePicture);
            userRepository.save(member);
        }
        return profilePicture;*/
        return null;
    }

    public ProfilePicture downloadProfilePicture(String email) {
/*        Optional<Member> optionalMember = userRepository.findById(email);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (member.getProfilePicture() != null) {
                Long id = member.getProfilePicture().getId();
                Optional<ProfilePicture> profilePicture = profilePictureRepository.findById(id);
                if (profilePicture.isPresent()) {
                    return profilePicture.get();

                } else {
                    throw new RecordNotFoundException();
                }
            } else {
                throw new RecordNotFoundException();
            }
        } else {
            throw new EmailNotFoundException(email);
        }*/
        return null;
    }
}