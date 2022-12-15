package com.example.Elivret.service;



import com.example.Elivret.model.Elivret;
import com.example.Elivret.model.Person;
import com.example.Elivret.model.Question;
import com.example.Elivret.model.Section;
import com.example.Elivret.repository.QuestionRepository;
import com.example.Elivret.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Configurable
@Transactional
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private SectionService sectionService;

    @Autowired
    private ElivretService elivretService;

    @Autowired
    private PersonService personService;

    public void createQuestion(Long sectionId, Question requestQuestion) {
        //find section
        Section section = sectionService.findSectionById(sectionId);
        requestQuestion.setSection(section);

        //set question properties
        questionRepository.save(requestQuestion);
    }

    public List<Question> findQuestionsBySectionId(Long sectionId) {
        List<Question> questions = questionRepository.findBySectionId(sectionId);
        return questions;
    }

    public Question findByQuestionId(Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new RuntimeException("cannot find section with id : " + questionId));
        return question;
    }
}
