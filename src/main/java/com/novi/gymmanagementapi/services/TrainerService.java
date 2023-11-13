package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.dto.NewTrainerDto;
import com.novi.gymmanagementapi.dto.TrainerDto;
import com.novi.gymmanagementapi.exceptions.EmailAlreadyTakenException;
import com.novi.gymmanagementapi.exceptions.EmailNotFoundException;
import com.novi.gymmanagementapi.models.Authority;
import com.novi.gymmanagementapi.models.Member;
import com.novi.gymmanagementapi.models.Trainer;
import com.novi.gymmanagementapi.repositories.MemberRepository;
import com.novi.gymmanagementapi.repositories.TrainerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final MemberRepository memberRepository;

    public TrainerService(TrainerRepository trainerRepository, MemberRepository memberRepository) {
        this.trainerRepository = trainerRepository;
        this.memberRepository = memberRepository;
    }

    public TrainerDto createTrainer(NewTrainerDto dto) {
        Optional<Trainer> optionalTrainer = trainerRepository.findById(dto.getEmail());
        if (optionalTrainer.isEmpty()) {
            Trainer model = new Trainer();
            model.setEmail(dto.getEmail());
            model.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
            model.setFirstname(dto.getFirstname());
            model.setLastname(dto.getLastname());
            model.setDateOfBirth(dto.getDateOfBirth());
            model.setHourlyRate(dto.getHourlyRate());
            model = trainerRepository.save(model);
            addAuthority(model.getEmail(), "ROLE_MEMBER");
            addAuthority(model.getEmail(), "ROLE_TRAINER");
            return asDTO(model);

        } else {
            throw new EmailAlreadyTakenException(dto.getEmail());
        }
    }

    public TrainerDto getTrainerAccount(String email) {
        Optional<Trainer> optionalTrainer = trainerRepository.findById(email);
        if (optionalTrainer.isPresent()) {
            TrainerDto dto = asDTO(optionalTrainer.get());
            return dto;

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public List<TrainerDto> getTrainers() {
        List<Trainer> trainerList = new ArrayList<>(trainerRepository.findAll());
        List<TrainerDto> trainerDtoList = new ArrayList<>();
        for (Trainer trainer : trainerList) {
            trainerDtoList.add(asDTO(trainer));
        }
        return trainerDtoList;
    }

    public TrainerDto getTrainer(String email) {
        Optional<Trainer> optionalTrainer = trainerRepository.findById(email);
        if (optionalTrainer.isPresent()) {
            return asDTO(optionalTrainer.get());

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public TrainerDto updateTrainer(String email, TrainerDto trainerDto) {
        Optional<Trainer> optionalTrainer = trainerRepository.findById(email);
        if (optionalTrainer.isPresent()) {
            Trainer trainer = optionalTrainer.get();
            trainer.setFirstname(trainerDto.getFirstname());
            trainer.setLastname(trainerDto.getLastname());
            trainer.setEmail(trainerDto.getEmail());
            trainer.setDateOfBirth(trainerDto.getDateOfBirth());
            trainerRepository.save(trainer);
            return asDTO(trainer);

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public void deleteTrainer(String email) {
        Optional<Trainer> optionalTrainer = trainerRepository.findById(email);
        if (optionalTrainer.isPresent()) {
            trainerRepository.deleteById(email);

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public void assignTrainer(String memberEmail, String trainerEmail) {
        Optional<Member> optionalMember = memberRepository.findById(memberEmail);
        Optional<Trainer> optionalTrainer = trainerRepository.findById(trainerEmail);
        if (optionalTrainer.isPresent() && optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.setTrainer(optionalTrainer.get());
            memberRepository.save(member);

        } else {
            throw new EmailNotFoundException(memberEmail);
        }
    }

    public void dismissTrainer(String email) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            //member.setTrainer(trainerRepository.getReferenceById((long) 1));
            memberRepository.save(member);

        } else {
            throw new EmailNotFoundException(email);
        }
    }
/*    public List<Long> getClientsFromTrainer(String email) {
        Optional<Trainer> optionalTrainer = trainerRepository.findById(email);
        if (optionalTrainer.isPresent()) {
            List<Long> list = new ArrayList<>();
            List<Member> memberList = memberRepository.findAllByTrainerId(optionalTrainer.get().getId());
            for (Member member : memberList) {
                list.add(member.getId());
            }
            return list;

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public Set<Authority> getAuthorities(String username) {
        if (!trainerRepository.existsById(username)) throw new UsernameNotFoundException(username);
        Member member = trainerRepository.findById(username).get();
        MemberDto memberDto = asDTO(member);
        return memberDto.getAuthorities();
    }

    public void removeAuthority(String username, String authority) {
        if (!trainerRepository.existsById(username)) throw new UsernameNotFoundException(username);
        Member member = trainerRepository.findById(username).get();
        Authority authorityToRemove = member.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        member.removeAuthority(authorityToRemove);
        memberRepository.save(member);
    }*/

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

    private TrainerDto asDTO(Trainer model) {
        TrainerDto dto = new TrainerDto();
        dto.setEmail(model.getEmail());
        dto.setPassword(model.getPassword());
        dto.setFirstname(model.getFirstname());
        dto.setLastname(model.getLastname());
        dto.setDateOfBirth(model.getDateOfBirth());
        dto.setHourlyRate(model.getHourlyRate());
        dto.setEnabled(model.isEnabled());
        dto.setAuthorities(model.getAuthorities());
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
