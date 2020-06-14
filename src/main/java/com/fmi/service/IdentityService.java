package com.fmi.service;

import com.fmi.domain.Authority;
import com.fmi.domain.Identity;
import com.fmi.domain.User;
import com.fmi.domain.dto.LectorDto;
import com.fmi.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class IdentityService {

    private final MailService mailService;

    private final UserService userService;

    @Autowired
    public IdentityService(MailService mailService, UserService userService) {
        this.mailService = mailService;
        this.userService = userService;
    }

    public void createIdentities(List<LectorDto> lectorDtos ){
        List<Identity> identities = lectorDtos
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
    }
    private Identity convertLectorDtoToIdentity(LectorDto lectorDto){
        Identity identity = new Identity();
        identity.setFullName(lectorDto.getName());
        identity.setScienceDegree(lectorDto.getScienceDegree());
        identity.setEducation(lectorDto.getEducation());
        identity.seteMail(lectorDto.getEmail());
        identity.setJob(lectorDto.getJob());
        identity.setPhoneNumber(lectorDto.getPhoneNumber());
        return identity;
    }

    private List<User> createUserIfNotExeist(Set<Identity> identities) {
       return  identities
               .stream()
               .map(i -> this.convertIdentityToUser(i))
               .map(u -> userService.registerUser(u, "12345"))
               .collect(Collectors.toList());
    }

    private UserDTO convertIdentityToUser(Identity identity) {
        UserDTO user = new UserDTO();
        user.setLogin(identity.geteMail());
        String fullname[] = identity.getFullName().split(" ");
        user.setFirstName(fullname[0]);
        user.setLastName(fullname[1]);
        user.setEmail(identity.geteMail());
//        user.setPassword("12345");
//        user.setAuthorities(new HashSet<>(Authority));
        return user;
    }
}
