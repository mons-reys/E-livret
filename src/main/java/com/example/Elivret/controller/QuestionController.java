package com.example.Elivret.controller;


import com.example.Elivret.model.Question;
import com.example.Elivret.model.Section;
import com.example.Elivret.service.QuestionService;
import com.example.Elivret.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin( origins = "*", maxAge = 3600 , exposedHeaders = {"Access-Control-Allow-Origin","Access-Control-Allow-Credentials"})
public class QuestionController {

    @Autowired
    private SectionService sectionService;

    @Autowired
    private QuestionService questionService;

    //remove add*
    @PostMapping ("/sections/{sectionId}/questions/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> createQuestion(@PathVariable(value = "sectionId") Long sectionId,
                                                                                          @RequestBody Question questionRequest) {
        questionService.createQuestion(sectionId, questionRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/sections/{sectionId}/questions")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or (hasAuthority('Tuteur') and @securityRoleService.isSectionOwner(#sectionId,authentication)) or (@securityRoleService.isSectionOwner(#sectionId,authentication) and @securityRoleService.isSectionVisibleAndForTheRightType(#sectionId,authentication))")
    public ResponseEntity<List<Question>> findAllSectionQuestions(@PathVariable(value = "sectionId") Long sectionId) {
        List<Question> questions = questionService.findQuestionsBySectionId(sectionId);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @PutMapping ("/sections/{sectionId}/questions/{questionId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity updateQuestion(@PathVariable(value = "questionId") Long questionId,
                                        @RequestBody String newContent) {
        questionService.updateQuestion(questionId,newContent);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PutMapping ("/sections/{sectionId}/AnswerQuestions/{questionId}")
    @PreAuthorize("@securityRoleService.isSectionOwner(#sectionId,authentication) and @securityRoleService.isSectionVisibleAndForTheRightType(#sectionId,authentication)")
    public ResponseEntity answer(@PathVariable(value = "questionId") Long questionId,
                                         @RequestBody String answer) {
        questionService.answer(questionId,answer);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PutMapping ("/sections/{sectionId}/questionsOptions/{questionId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity updateQuestion2(@PathVariable(value = "questionId") Long questionId,
                                         @RequestBody Question question) {
        questionService.updateQuestion2(questionId,question.getContent(),question.getOptions());
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @DeleteMapping("/sections/{sectionId}/questions/{questionId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity deleteQuestion(@PathVariable(value = "questionId") Long questionId)
    {
    	questionService.deleteQuestion(questionId);
        return new ResponseEntity(HttpStatus.OK);
    }

    
}
