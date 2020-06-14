package com.fmi.service;

import com.fmi.domain.Discipline;
import com.fmi.domain.Identity;
import com.fmi.domain.User;
import com.fmi.domain.dto.RecordDto;
import com.fmi.repository.DisciplineRepository;
import com.fmi.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IdentityService {

    private final MailService mailService;

    private final UserService userService;

    private final DisciplineRepository disciplineRepository;

    @Autowired
    public IdentityService(MailService mailService, UserService userService, DisciplineRepository disciplineRepository) {
        this.mailService = mailService;
        this.userService = userService;
        this.disciplineRepository = disciplineRepository;
    }

    public void createIdentities(List<RecordDto> recordDtos){
        List<Identity> identities = recordDtos
                .stream()
                .map( l -> this.convertLectorDtoToIdentity(l))
                .collect(Collectors.toList());
        identities.forEach( System.out::println);
        Set<Identity> identitySet = new HashSet<>(identities);
        System.out.println("_________________________");
        identitySet.forEach( System.out::println);
        System.out.println("Finish with creation of identities ");
        createUserIfNotExeist(identitySet);
        this.mailService.sendEmail("stoyan.ivanov@dreamix.eu", "test", "test", false, false);
        createDiscilineIfNotExist(recordDtos);
    }
    private Identity convertLectorDtoToIdentity(RecordDto recordDto){
        Identity identity = new Identity();
        identity.setFullName(recordDto.getName());
        identity.setScienceDegree(recordDto.getScienceDegree());
        identity.setEducation(recordDto.getEducation());
        identity.seteMail(recordDto.getEmail());
        identity.setJob(recordDto.getJob());
        identity.setPhoneNumber(recordDto.getPhoneNumber());
        return identity;
    }

    private List<User> createUserIfNotExeist(Set<Identity> identities) {
       return  identities
               .stream()
               .map(i -> this.convertIdentityToUser(i))
               .map(u -> userService.registerUser(u, "12345"))
               .collect(Collectors.toList());
    }

    private List<Discipline> createDiscilineIfNotExist(List<RecordDto> rows) {
        List<Discipline>  disciplines = rows
                .stream()
                .map(this::converRowToDiscipline)
                .filter(this::isDisciplineNotExist)
                .collect(Collectors.toList());
        return this.disciplineRepository.saveAll(disciplines);
    }
    private boolean isDisciplineNotExist(Discipline discipline) {
        Optional<Discipline> optional=  this.disciplineRepository.findFirstByDescription(discipline.getDescription());

        if(optional.isPresent()) {
            return false;
        }
        return true;
    }

    private UserDTO convertIdentityToUser(Identity identity) {
        UserDTO user = new UserDTO();
        user.setLogin(identity.geteMail());
        String fullname[] = identity.getFullName().split(" ");
        user.setFirstName(fullname[0]);
        user.setLastName(fullname[1]);
        user.setEmail(identity.geteMail());
        return user;
    }

    private Discipline converRowToDiscipline(RecordDto record) {
        Discipline discipline = new Discipline();

        discipline.setDescription(record.getDiscipline());
        discipline.setDisciplineType(record.getDisciplineKind());
        return discipline;
    }
}
