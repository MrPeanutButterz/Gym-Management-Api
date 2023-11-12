package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.dto.TrainerDto;
import com.novi.gymmanagementapi.exceptions.EmailNotFoundException;
import com.novi.gymmanagementapi.models.Member;
import com.novi.gymmanagementapi.models.Trainer;
import com.novi.gymmanagementapi.repositories.MemberRepository;
import com.novi.gymmanagementapi.repositories.TrainerRepository;
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

    public TrainerDto createTrainer(TrainerDto trainerDto) {
        Trainer trainer = trainerRepository.save(transformToMODEL(trainerDto));
        return transformToDTO(trainer);
    }

    public List<TrainerDto> getTrainers() {
        List<Trainer> trainerList = new ArrayList<>(trainerRepository.findAll());
        List<TrainerDto> trainerDtoList = new ArrayList<>();
        for (Trainer trainer : trainerList) {
            trainerDtoList.add(transformToDTO(trainer));
        }
        return trainerDtoList;
    }

    public TrainerDto getTrainer(String email) {
        Optional<Trainer> optionalTrainer = trainerRepository.findById(email);
        if (optionalTrainer.isPresent()) {
            return transformToDTO(optionalTrainer.get());

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public TrainerDto updateTrainer(String email, TrainerDto trainerDto) {
        Optional<Trainer> optionalTrainer = trainerRepository.findById(email);
        if (optionalTrainer.isPresent()) {
            Trainer trainer = optionalTrainer.get();
            trainer.setFirstName(trainerDto.getFirstName());
            trainer.setLastName(trainerDto.getLastName());
            trainer.setEmail(trainerDto.getEmail());
            trainer.setDateOfBirth(trainerDto.getDob());
            trainerRepository.save(trainer);
            return transformToDTO(trainer);

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

    public TrainerDto assignTrainer(String email, long memberID) {
        Optional<Trainer> optionalTrainer = trainerRepository.findById(email);
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalTrainer.isPresent() && optionalMember.isPresent()) {
            Member member = optionalMember.get();
            //member.setTrainer(optionalTrainer.get());
            memberRepository.save(member);
            return transformToDTO(optionalTrainer.get());

        } else {
            throw new EmailNotFoundException(email);
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
/*
    public List<Long> getClientsFromTrainer(String email) {
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
    }*/

    private TrainerDto transformToDTO(Trainer model) {
        TrainerDto dto = new TrainerDto();
        dto.setFirstName(model.getFirstName());
        dto.setLastName(model.getLastName());
        dto.setEmail(model.getEmail());
        return dto;
    }

    private Trainer transformToMODEL(TrainerDto dto) {
        Trainer model = new Trainer();
        model.setFirstName(dto.getFirstName());
        model.setLastName(dto.getLastName());
        model.setEmail(dto.getEmail());
        return model;
    }
}
