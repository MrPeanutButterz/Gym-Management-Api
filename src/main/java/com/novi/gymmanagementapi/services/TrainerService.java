package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.dto.FullMemberDto;
import com.novi.gymmanagementapi.dto.FullTrainerDto;
import com.novi.gymmanagementapi.dto.PartMemberDto;
import com.novi.gymmanagementapi.dto.PartTrainerDto;
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

    public TrainerService(TrainerRepository trainerRepository, MemberRepository memberRepository) {
        this.trainerRepository = trainerRepository;
        this.memberRepository = memberRepository;
    }

    public List<PartTrainerDto> getTrainers() {
        List<Trainer> trainerList = new ArrayList<>(trainerRepository.findAll());
        List<PartTrainerDto> trainerDtoList = new ArrayList<>();
        for (Trainer trainer : trainerList) {
            PartTrainerDto dto = new PartTrainerDto();
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
            member.setTrainer(null);
            memberRepository.save(member);

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public FullTrainerDto getTrainerAccount(String email) {
        Optional<Trainer> optionalTrainer = trainerRepository.findById(email);
        if (optionalTrainer.isPresent()) {
            return asDTO(optionalTrainer.get());

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public FullTrainerDto createTrainerAccount(FullTrainerDto dto) {
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

    public FullTrainerDto updateTrainer(String email, FullTrainerDto fullTrainerDto) {
        Optional<Trainer> optionalTrainer = trainerRepository.findById(email);
        if (optionalTrainer.isPresent()) {
            Trainer trainer = optionalTrainer.get();
            trainer.setPassword(fullTrainerDto.getPassword());
            trainer.setFirstname(fullTrainerDto.getFirstname());
            trainer.setLastname(fullTrainerDto.getLastname());
            trainer.setDateOfBirth(fullTrainerDto.getDateOfBirth());
            trainer.setHourlyRate(fullTrainerDto.getHourlyRate());
            trainerRepository.save(trainer);
            return asDTO(trainer);

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public void deleteTrainerAccount(String email) {
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

    public List<PartMemberDto> getClients(String email) {
        Optional<Trainer> optionalTrainer = trainerRepository.findById(email);
        if (optionalTrainer.isPresent()) {
            List<Member> members = memberRepository.findAllByTrainerIs(optionalTrainer.get());
            List<PartMemberDto> partMemberDtoList = new ArrayList<>();
            for (Member m : members) {
                PartMemberDto partMemberDto = new PartMemberDto();
                partMemberDto.setEmail(m.getEmail());
                partMemberDto.setFirstname(m.getFirstname());
                partMemberDto.setLastname(m.getLastname());
                partMemberDto.setDateOfBirth(m.getDateOfBirth());
                partMemberDto.setMembership(m.getMembership());
                partMemberDtoList.add(partMemberDto);
            }
            return partMemberDtoList;

        } else {
            throw new UsernameNotFoundException(email);
        }
    }

    public Set<Authority> getAuthorities(String email) {
        Optional<Trainer> optionalTrainer = trainerRepository.findById(email);
        if (optionalTrainer.isPresent()) {
            Trainer trainer = optionalTrainer.get();
            return trainer.getAuthorities();

        } else {
            throw new UsernameNotFoundException(email);
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

    private FullTrainerDto asDTO(Trainer model) {
        FullTrainerDto dto = new FullTrainerDto();
        dto.setEmail(model.getEmail());
        dto.setPassword("********************************");
        dto.setFirstname(model.getFirstname());
        dto.setLastname(model.getLastname());
        dto.setDateOfBirth(model.getDateOfBirth());
        dto.setHourlyRate(model.getHourlyRate());
        dto.setEnabled(model.isEnabled());
        return dto;
    }

    private Trainer asMODEL(FullTrainerDto dto) {
        Trainer model = new Trainer();
        model.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        model.setEmail(dto.getEmail());
        model.setFirstname(dto.getFirstname());
        model.setLastname(dto.getLastname());
        model.setDateOfBirth(dto.getDateOfBirth());
        model.setHourlyRate(dto.getHourlyRate());
        model.setHourlyRate(dto.getHourlyRate());
        model.setEnabled(true);
        return model;
    }

/*





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
    }



*/


}
