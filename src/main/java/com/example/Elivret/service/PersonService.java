package com.example.Elivret.service;



import com.example.Elivret.model.Person;
import com.example.Elivret.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Configurable
@Transactional
public class PersonService {

    @Autowired
    private PersonRepository personRepository;


    public void createPerson(Person requestPerson) {
        Person person = personRepository.save(requestPerson);
    }

}
