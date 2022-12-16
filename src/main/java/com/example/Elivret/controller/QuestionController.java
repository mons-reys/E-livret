package com.example.Elivret.controller;


import com.example.Elivret.model.Question;
import com.example.Elivret.model.Section;
import com.example.Elivret.service.QuestionService;
import com.example.Elivret.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = "token")
public class QuestionController {

    @Autowired
    private SectionService sectionService;

    @Autowired
    private QuestionService questionService;

    //remove add*
    @PostMapping ("/sections/{sectionId}/questions/add")
    public ResponseEntity createQuestion(@PathVariable(value = "sectionId") Long sectionId,
                                        @RequestBody Question questionRequest) {
        questionService.createQuestion(sectionId, questionRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/sections/{sectionId}/questions")
    public ResponseEntity<List<Question>> findAllSectionQuestions(@PathVariable(value = "sectionId") Long sectionId) {
        List<Question> questions = questionService.findQuestionsBySectionId(sectionId);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @PutMapping ("/sections/{sectionId}/questions/{questionId}")
    public ResponseEntity updateQuestion(@PathVariable(value = "questionId") Long questionId,
                                        @RequestBody String newContent) {
        questionService.updateQuestion(questionId,newContent);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @DeleteMapping("/sections/{sectionId}/questions/{questionId}")
    public ResponseEntity deleteQuestion(@PathVariable(value = "questionId") Long questionId)
    {
    	questionService.deleteQuestion(questionId);
        return new ResponseEntity(HttpStatus.OK);
    }

}