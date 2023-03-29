package com.example.Elivret.controller;

import com.example.Elivret.model.Elivret;

import com.example.Elivret.model.Person;
import com.example.Elivret.model.PersonDTO;
import com.example.Elivret.model.Section;
import com.example.Elivret.security.UserService;
import com.example.Elivret.service.PersonService;
import com.example.Elivret.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin( origins = "*", maxAge = 3600 , exposedHeaders = {"Access-Control-Allow-Origin","Access-Control-Allow-Credentials"})
public class SectionController {

	@Autowired
	private SectionService sectionService;

    @Autowired
    private PersonService personService;

    @Autowired
    private UserService userService;



	@PutMapping ("/elivret/sections/{sectionId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity updateSection(@PathVariable(value = "sectionId") Long elivretId,
                                        @RequestBody String title) {
        sectionService.updateSection(elivretId,title);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping ("/elivret/sections/{sectionId}/updatePersonType")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity updatePersonType(@PathVariable(value = "sectionId") Long elivretId,
                                        @RequestBody String persontype) {
        sectionService.updatePersonType(elivretId,persontype);
        return new ResponseEntity(HttpStatus.OK);
    }

	@DeleteMapping ("/elivret/sections/{sectionId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity deleteSection(@PathVariable(value = "sectionId") Long sectionId) {
        sectionService.deleteSection(sectionId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping ("/elivret/sections/{sectionId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity getSectionById(@PathVariable(value = "sectionId") Long sectionId) {
       Section section =  sectionService.findSectionById(sectionId);
        return new ResponseEntity(section, HttpStatus.OK);
    }

    //remove add*
    @PostMapping ("/elivret/{elivretId}/sections/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity createSection(@PathVariable(value = "elivretId") Long elivretId,
                                        @RequestBody Section sectionRequest) {
        sectionService.createSection(elivretId, sectionRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/elivret/{elivretId}/sections")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Section>> findAlleLivretSections(@PathVariable(value = "elivretId") Long elivretId) {
        List<Section> sections = sectionService.findSectionsByElivretId(elivretId);
        return new ResponseEntity<>(sections, HttpStatus.OK);
    }

    @PostMapping ("/elivret/sections/{sectionId}/updateVisibility")
    @PreAuthorize("hasAuthority('Tuteur')")
    public ResponseEntity createSection(@PathVariable(value = "sectionId") Long sectionId,
                                        @RequestBody boolean visibility) {
        sectionService.updateVisibility(sectionId, visibility);
        return new ResponseEntity(HttpStatus.OK);
    }






}