package com.example.Elivret.controller;


import com.example.Elivret.model.Elivret;
import com.example.Elivret.model.Section;
import com.example.Elivret.service.ElivretService;
import com.example.Elivret.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = "token")
public class SectionController {

    @Autowired
    private SectionService sectionService;

    @PostMapping ("/elivret/{elivretId}/sections/add")
    public ResponseEntity createSection(@PathVariable(value = "elivretId") Long elivretId,
                                        @RequestBody Section sectionRequest) {
        sectionService.createSetion(elivretId, sectionRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/elivret/{elivretId}/sections")
    public ResponseEntity<List<Section>> getAllelivretSections(@PathVariable(value = "elivretId") Long elivretId) {
        List<Section> sections = sectionService.getSectionsByElivretId(elivretId);
        return new ResponseEntity<>(sections, HttpStatus.OK);
    }
    
    @PutMapping ("/elivret/{elivretId}/section/{sectionId}")
    public ResponseEntity updateElivret(@PathVariable(value = "sectionId") Long elivretId,
                                        @RequestBody String title, @RequestBody Person person) {
        sectionService.updateSection(elivretId,title);
        sectionService.updateSectionPerson(elivretId,person);
        return new ResponseEntity(HttpStatus.OK);
    }


}