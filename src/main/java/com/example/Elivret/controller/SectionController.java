package com.example.Elivret.controller;

import com.example.Elivret.model.Elivret;

import com.example.Elivret.model.Person;
import com.example.Elivret.model.Section;
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


	@PutMapping ("/elivret/{elivretId}/section/{sectionId}")
    public ResponseEntity updateSection(@PathVariable(value = "sectionId") Long elivretId,
                                        @RequestBody String title, @RequestBody Person person) {
        sectionService.updateSection(elivretId,title);
        sectionService.updateSectionPerson(elivretId,person);
        return new ResponseEntity(HttpStatus.OK);
    }

	@DeleteMapping ("/elivret/{elivretId}/section/{sectionId}")
    public ResponseEntity deleteSection(@PathVariable(value = "sectionId") Long sectionId) {
        sectionService.deleteSection(sectionId);
        return new ResponseEntity(HttpStatus.OK);
    }

    //remove add*
    @PostMapping ("/elivret/{elivretId}/sections/add")
    public ResponseEntity createSection(@PathVariable(value = "elivretId") Long elivretId,
                                        @RequestBody Section sectionRequest) {
        sectionService.createSection(elivretId, sectionRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/elivret/{elivretId}/sections")
    public ResponseEntity<List<Section>> findAlleLivretSections(@PathVariable(value = "elivretId") Long elivretId) {
        List<Section> sections = sectionService.findSectionsByElivretId(elivretId);
        return new ResponseEntity<>(sections, HttpStatus.OK);
    }

}