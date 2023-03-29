package com.example.Elivret.service;

import com.example.Elivret.model.Person;
import com.example.Elivret.model.Section;
import com.example.Elivret.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service("securityRoleService")
public class SecurityRoleService {

    @Autowired
    PersonService PersonService;

    @Autowired
    SectionService SectionService;

    @Autowired
    UserService userService;

    public boolean isOwner(Long elivretId,Authentication authentication) {
        UserDetails authenticatedUser = (UserDetails) authentication.getPrincipal();
        Long userId = userService.search(authenticatedUser.getUsername()).getId();
        System.out.println(authentication.getPrincipal());
        return PersonService.isPersonInElivret(userId,elivretId);
    }

    public boolean isSectionOwner(Long sectionId,Authentication authentication) {
        UserDetails authenticatedUser = (UserDetails) authentication.getPrincipal();
        Long userId = userService.search(authenticatedUser.getUsername()).getId();

        Long elivretId = SectionService.findSectionById(sectionId).getElivret().getId();

        return PersonService.isPersonInElivret(userId,elivretId);
    }

    public boolean isSectionVisibleAndForTheRightType(Long sectionId,Authentication authentication) {
        UserDetails authenticatedUser = (UserDetails) authentication.getPrincipal();
        Long userId = userService.search(authenticatedUser.getUsername()).getId();
        Person user = PersonService.getPersonById(userId);


        Section section = SectionService.findSectionById(sectionId);

        if (section.isVisibility()) {
            PersonService.isSectionForPersonType(user.getId(), section);
        }
        return false;
    }

    public boolean isTheAuthenticatedUser(Long personId, Authentication authentication) {
        UserDetails authenticatedUser = (UserDetails) authentication.getPrincipal();
        Long userId = userService.search(authenticatedUser.getUsername()).getId();

        return personId == userId;
    }

}