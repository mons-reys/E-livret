package com.example.Elivret.service;



import com.example.Elivret.model.Person;
import com.example.Elivret.model.Section;
import com.example.Elivret.repository.PersonRepository;
import com.example.Elivret.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
@Configurable
@Transactional
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private UserService userService;



    public void createPerson(Person requestPerson) {
        Person person = personRepository.save(requestPerson);
    }

    public String registerPersonWithSection(Person requestPerson, Long sectionId){
        Section section = sectionService.findSectionById(sectionId);
        Person person1= new Person();

        person1.setEmail( requestPerson.getEmail().toLowerCase() );
        person1.setUserName(requestPerson.getUserName());

        String password = new Random().ints(10, 33, 122).collect(StringBuilder::new,
                        StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();


        person1.setPassword(password);

        Set<String> roles = new HashSet<String>();
        roles.add(requestPerson.getPersonType());
        person1.setRoles(roles);


        String token = userService.signup(person1);
        section.setPerson(person1);
        
        String url = generateInvitationLink(sectionId, person1.getUserName(), password);

        return url;
    }

    public String generateInvitationLink(long sectionId, String username, String password) {
        UriComponentsBuilder builder =  UriComponentsBuilder.newInstance();
        UriComponents uriComponents = builder.path("/section/{sectionId}/take")
                .queryParam("username", username)
                .queryParam("password", password)
                .buildAndExpand(sectionId);
        return uriComponents.toUriString();
    }


}
