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

    public void updateQuestion(Long questionId, String newContent) {
        //find question
        Question question = findByQuestionId(questionId);

        //set question new content
        question.setContent(newContent);
        questionRepository.save(question);
    }
    public void answer(Long questionId, String answer) {
        //find question
        Question question = findByQuestionId(questionId);

        //set question new content
        question.setAnswer(answer);
        questionRepository.save(question);
    }
    public void updateQuestion2(Long questionId, String newContent,List<String> options) {
        //find question
        Question question = findByQuestionId(questionId);

        //set question new content
        
        question.setContent(newContent);
       question.setOptions(options);
        questionRepository.save(question);
    }
    
    public void deleteQuestion(Long questionId) {
        //find question
        Question question = findByQuestionId(questionId);

        questionRepository.delete(question);
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

