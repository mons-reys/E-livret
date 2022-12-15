package com.example.Elivret.service;



import com.example.Elivret.model.Elivret;
import com.example.Elivret.model.Person;
import com.example.Elivret.model.Section;
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




    public void createSection(Long elivretId, Section requestSection) {

        //find elivret
        Elivret elivret = elivretService.findElivretById(elivretId);

        //manual create admin for persistance exception
        Person admin = new Person();
        admin.setFirstName("admin");
        personService.createPerson(admin);

        //set Section properties
        requestSection.setPerson(admin);
        requestSection.setElivret(elivret);
        sectionRepository.save(requestSection);
    }

    public List<Section> findSectionsByElivretId(Long elivretId) {
        List<Section> sections = sectionRepository.findByElivretId(elivretId);
        return sections;
    }

    public Section findSectionById(Long sectionId) {
        Section section = sectionRepository.findById(sectionId).orElseThrow(() -> new RuntimeException("cannot find section with id : " + sectionId));
        return section;
    }
}
