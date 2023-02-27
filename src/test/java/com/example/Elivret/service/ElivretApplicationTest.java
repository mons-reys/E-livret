package com.example.Elivret.service;

import com.example.Elivret.model.*;
import com.example.Elivret.service.ElivretService;
import com.example.Elivret.service.QuestionService;
import com.example.Elivret.service.SectionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class ElivretApplicationTest {
    @Autowired
    ElivretService elivretService;

    @Autowired
    SectionService sectionService;

    @Autowired
    QuestionService questionService;

    @Autowired
    PersonService personService;

    @Test
    public void testCreateAndGetAndDeleteElivret() {
        Elivret p = new Elivret();
        p.setTitle("E2");
        elivretService.createElivret(p);
        List<Elivret> list = elivretService.findAllElivrets();
        assertEquals("E2", list.get(0).getTitle());
        assertEquals("E2", p.getTitle());
        System.out.println(p.getId());
        elivretService.deleteById(p.getId());
        //assertEquals(null, elivretService.findElivretById(p.getId()));
    }

    @Test
    public void testCreateAndGetSeciton() {
        Elivret elivret = new Elivret();
        elivret.setTitle("E2");
        elivretService.createElivret(elivret);

        Section section1 = new Section();
        section1.setTitle("section Title 1");
        section1.setElivret(elivret);

        Section section2 = new Section();
        section2.setTitle("section Title 2");
        section2.setElivret(elivret);

        List<Section> sections = sectionService.findSectionsByElivretId(elivret.getId());

        System.out.println(sections);

        //assertEquals("section Title 2", sections.get(1).getTitle());
        //assertEquals("section Title", section1.getTitle());
    }


    @Test
    public void testCreateAndGetQuestion() {

        Elivret elivret = new Elivret();
        elivret.setTitle("E2");
        elivretService.createElivret(elivret);


        Section section = new Section();
        section.setTitle("section 1");
        sectionService.createSection(elivret.getId(), section);

        Question question = new Question();
        question.setContent("question 1");
        question.setType(QuestionType.text);


        List<String> options = new ArrayList<String>();
        options.add("option 1 ");
        question.setOptions(options);

        String answer = "answer 1";
        question.setAnswer(answer);


        questionService.createQuestion(section.getId(), question);


        Question result = questionService.findByQuestionId(question.getId());


        assertEquals("question 1", result.getContent());
    }

    @Test
    public void testLinkPersonWithTwoLivrets() {
        Person person = new Person();
        person.setUserName("mario@gmail.com");
        person.setEmail("mario@gmail.com");
        person.setPassword("mario@gmail.com");
        person.setPersonType("Tuteur");


        Elivret elivret1 = new Elivret();
        elivret1.setTitle("E1");
        elivretService.createElivret(elivret1);

        List<Person> personList = new ArrayList<Person>();
        personList.add(person);
        elivret1.setPersons(personList);

        String url = personService.registerPersonWithLivret(person, elivret1.getId(), "test");
        assertEquals("test/login?username=mario@gmail.com", url);



        Elivret elivret2 = new Elivret();
        elivret2.setTitle("E2");
        elivretService.createElivret(elivret2);

        person.setPersonType("Alternant");


        String url2 = personService.registerPersonWithLivret(person, elivret2.getId(), "test");
        assertEquals("test/login?username=mario@gmail.com", url2);
    }
}