package com.example.Elivret.service;



import com.example.Elivret.model.Admin;
import com.example.Elivret.model.Elivret;
import com.example.Elivret.model.Person;
import com.example.Elivret.model.Section;
import com.example.Elivret.repository.ElivretRepository;
import com.example.Elivret.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Configurable
@Transactional
public class SectionService {
    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private ElivretService elivretService;

    @Autowired
    private PersonService personService;
    
    public void updateSection(Long sectionId,String title){
        Section sectionToUpdate = sectionRepository.findById(sectionId);
        sectionToUpdate.setTitle(title);
        sectionRepository.save(sectionToUpdate);
    }

    public void updateSectionPerson(Long sectionId,Person person){
        Section sectionToUpdate = sectionRepository.findById(sectionId);
        sectionToUpdate.setPerson(person);
        sectionRepository.save(sectionToUpdate);
    }

    public void deleteSection(Long sectionId){
        Section sectionToDelete = sectionRepository.findbyid(sectionId);
        sectionRepository.delete(sectionToDelete);
    }


    public void createSetion(Long elivretId, Section requestSection) {

        //find elivret
        Elivret elivret = elivretService.findElivretById(elivretId);

        //manual create admin for persistance exception
        Person admin = new Admin();
        admin.setFirstName("admin");
        personService.createPerson(admin);

        //set Section properties
        requestSection.setPerson(admin);
        requestSection.setElivret(elivret);
        sectionRepository.save(requestSection);
    }

    public List<Section> getSectionsByElivretId(Long elivretId) {
        List<Section> sections = sectionRepository.findByElivretId(elivretId);
        return sections;
    }

    public Section getSectionById(Long sectionId) {
        Section section = sectionRepository.findById(sectionId).orElseThrow(() -> new RuntimeException("cannot find section with id : " + sectionId));
        return section;
    }
}
