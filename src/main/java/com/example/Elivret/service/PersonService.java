package com.example.Elivret.service;



import com.example.Elivret.model.Elivret;
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

import java.util.*;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;


    @Autowired
    private ElivretService elivretService;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private UserService userService;



    public void createPerson(Person requestPerson) {
        Person person = personRepository.save(requestPerson);
    }

    public String registerPersonWithLivret(Person requestPerson, Long elivretId, String domaineName){

        boolean personExist = personRepository.existsByEmail( requestPerson.getEmail().toLowerCase() );
        System.out.println(personExist);

        Person personToSave;
        String password;
        if(!personExist){
            personToSave= new Person();
            personToSave.setEmail( requestPerson.getEmail().toLowerCase() );
            personToSave.setUserName(requestPerson.getUserName());

            password = new Random().ints(10, 33, 122).collect(StringBuilder::new,
                            StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();


            personToSave.setPassword(password);
            personToSave.setPersonType(requestPerson.getPersonType());

            Set<String> roles = new HashSet<String>();
            roles.add(requestPerson.getPersonType());
            personToSave.setRoles(roles);
            userService.signup(personToSave);
        }else{
            System.out.println("else");
            personToSave = personRepository.findByEmail(requestPerson.getEmail().toLowerCase());
        }

        Elivret elivret = elivretService.getElivretById(elivretId);

        System.out.println(elivret);
        System.out.println(personToSave);

        List<Person> persons = elivret.getPersons();

        if( !containsPersonType(persons, personToSave.getPersonType()) ){
            System.out.println("before " + elivret.getPersons());
            persons.add(personToSave);
            System.out.println("after " + elivret.getPersons());
            System.out.println("personToSave");
        }else{
            throw new RuntimeException("person Type exist");
        }

        elivret.setPersons(persons);
        System.out.println("last " + elivret);
        elivretService.createElivret(elivret);

        String url = generateInvitationLink(elivretId, requestPerson.getUserName(), personToSave.getPassword(),domaineName);

        return url;
    }


    private boolean containsPersonType(List<Person> persons, String personType){
        boolean result = false;
        for(int i = 0; i < persons.size(); i++ ){
            if( persons.get(i).getPersonType().equals(personType) ){
                result = true;
                break;
            }
        }
        return result;
    }

    public String generateInvitationLink(long elivretId, String username, String password, String domaineName) {
        UriComponentsBuilder builder =  UriComponentsBuilder.newInstance();
        UriComponents uriComponents = builder.path(domaineName + "/login")
                .queryParam("username", username)
                .queryParam("password", password)
                .buildAndExpand(elivretId);
        return uriComponents.toUriString();
    }


}
