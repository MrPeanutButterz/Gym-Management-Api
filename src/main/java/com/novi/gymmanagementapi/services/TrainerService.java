package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.dto.TrainerDto;
import com.novi.gymmanagementapi.dto.MemberResponseDto;
import com.novi.gymmanagementapi.dto.TrainerResponseDto;
import com.novi.gymmanagementapi.exceptions.EmailAlreadyTakenException;
import com.novi.gymmanagementapi.exceptions.EmailNotFoundException;
import com.novi.gymmanagementapi.models.Authority;
import com.novi.gymmanagementapi.models.Member;
import com.novi.gymmanagementapi.models.Trainer;
import com.novi.gymmanagementapi.repositories.MemberRepository;
import com.novi.gymmanagementapi.repositories.TrainerRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final MemberRepository memberRepository;

    public TrainerService(TrainerRepository trainerRepository,
                          MemberRepository memberRepository) {
        this.trainerRepository = trainerRepository;
        this.memberRepository = memberRepository;
    }

    public List<TrainerResponseDto> getTrainers() {
        List<Trainer> trainerList = new ArrayList<>(trainerRepository.findAll());
        List<TrainerResponseDto> trainerDtoList = new ArrayList<>();
        for (Trainer trainer : trainerList) {
            TrainerResponseDto dto = new TrainerResponseDto();
            dto.setEmail(trainer.getEmail());
            dto.setFirstname(trainer.getFirstname());
            dto.setLastname(trainer.getLastname());
            dto.setDateOfBirth(trainer.getDateOfBirth());
            dto.setHourlyRate(trainer.getHourlyRate());
            trainerDtoList.add(dto);
        }
        return trainerDtoList;
    }

    public void assignTrainer(String memberEmail, String trainerEmail) {
        Optional<Member> optionalMember = memberRepository.findById(memberEmail);
        if (optionalMember.isPresent()) {
            Optional<Trainer> optionalTrainer = trainerRepository.findById(trainerEmail);
            if (optionalTrainer.isPresent()) {
                Member member = optionalMember.get();
                member.setTrainer(optionalTrainer.get());
                memberRepository.save(member);

            } else {
                throw new EmailNotFoundException(trainerEmail);
            }
        } else {
            throw new EmailNotFoundException(memberEmail);
        }
    }

    public void dismissTrainer(String email) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.setTrainer(null);
            memberRepository.save(member);

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public TrainerResponseDto getTrainerAccount(String email) {
        Optional<Trainer> optionalTrainer = trainerRepository.findById(email);
        if (optionalTrainer.isPresent()) {
            return asDTO(optionalTrainer.get());

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public TrainerResponseDto createTrainerAccount(TrainerDto dto) {
        Optional<Trainer> optionalTrainer = trainerRepository.findById(dto.getEmail());
        if (optionalTrainer.isEmpty()) {
            Trainer trainer = asMODEL(dto);
            trainer = trainerRepository.save(trainer);
            addAuthority(trainer.getEmail(), "ROLE_MEMBER");
            addAuthority(trainer.getEmail(), "ROLE_TRAINER");
            return asDTO(trainer);

        } else {
            throw new EmailAlreadyTakenException(dto.getEmail());
        }
    }

    public TrainerResponseDto updateTrainer(String email, TrainerDto trainerDto) {
        Optional<Trainer> optionalTrainer = trainerRepository.findById(email);
        if (optionalTrainer.isPresent()) {
            Trainer trainer = optionalTrainer.get();
            trainer.setPassword(new BCryptPasswordEncoder().encode(trainerDto.getPassword()));
            trainer.setFirstname(trainerDto.getFirstname());
            trainer.setLastname(trainerDto.getLastname());
            trainer.setDateOfBirth(trainerDto.getDateOfBirth());
            trainer.setHourlyRate(trainerDto.getHourlyRate());
            trainerRepository.save(trainer);
            return asDTO(trainer);

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public void disableTrainerAccount(String email) {
        Optional<Trainer> optionalTrainer = trainerRepository.findById(email);
        if (optionalTrainer.isPresent()) {
            Trainer trainer = optionalTrainer.get();
            trainer.setEnabled(false);
            trainerRepository.save(trainer);
            removeAuthority(email, "ROLE_MEMBER");
            removeAuthority(email, "ROLE_TRAINER");

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public List<MemberResponseDto> getClients(String email) {
        Optional<Trainer> optionalTrainer = trainerRepository.findById(email);
        if (optionalTrainer.isPresent()) {
            List<Member> members = memberRepository.findAllByTrainerIs(optionalTrainer.get());
            List<MemberResponseDto> memberResponseDtoList = new ArrayList<>();
            for (Member m : members) {
                MemberResponseDto memberResponseDto = new MemberResponseDto();
                memberResponseDto.setFirstname(m.getFirstname());
                memberResponseDto.setEmail(m.getEmail());
                memberResponseDto.setLastname(m.getLastname());
                memberResponseDto.setDateOfBirth(m.getDateOfBirth());
                memberResponseDto.setMembership(m.getMembership());
                memberResponseDto.setGoalIDs(m.getGoalIDs());
                memberResponseDto.setTrainer(modelToResponse(m.getTrainer()));
                memberResponseDtoList.add(memberResponseDto);

            }
            return memberResponseDtoList;

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public void removeAuthority(String email, String authority) {
        if (!trainerRepository.existsById(email)) throw new UsernameNotFoundException(email);
        Trainer trainer = trainerRepository.findById(email).get();
        Authority authorityToRemove = trainer.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        trainer.removeAuthority(authorityToRemove);
        trainerRepository.save(trainer);
    }

    public void addAuthority(String email, String authority) {
        Optional<Trainer> optionalTrainer = trainerRepository.findById(email);
        if (optionalTrainer.isPresent()) {
            Trainer model = optionalTrainer.get();
            model.addAuthority(new Authority(email, authority));
            trainerRepository.save(model);

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    private TrainerResponseDto modelToResponse(Trainer model) {
        TrainerResponseDto dto = new TrainerResponseDto();
        dto.setEmail(model.getEmail());
        dto.setFirstname(model.getFirstname());
        dto.setLastname(model.getLastname());
        dto.setDateOfBirth(model.getDateOfBirth());
        return dto;
    }

    private TrainerResponseDto asDTO(Trainer model) {
        TrainerResponseDto dto = new TrainerResponseDto();
        dto.setEmail(model.getEmail());
        dto.setFirstname(model.getFirstname());
        dto.setLastname(model.getLastname());
        dto.setDateOfBirth(model.getDateOfBirth());
        dto.setHourlyRate(model.getHourlyRate());
        return dto;
    }

    private Trainer asMODEL(TrainerDto dto) {
        Trainer model = new Trainer();
        model.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        model.setEmail(dto.getEmail());
        model.setFirstname(dto.getFirstname());
        model.setLastname(dto.getLastname());
        model.setDateOfBirth(dto.getDateOfBirth());
        model.setHourlyRate(dto.getHourlyRate());
        model.setEnabled(true);
        return model;
    }
}
