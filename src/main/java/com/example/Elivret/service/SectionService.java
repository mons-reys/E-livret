package com.example.Elivret.service;



import com.example.Elivret.model.Elivret;
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


    public void createSetion(Long elivretId, Section requestSection) {
        Elivret elivret = elivretService.findElivretById(elivretId);
        requestSection.setElivret(elivret);
        sectionRepository.save(requestSection);
    }

    public List<Section> getSectionsByElivretId(Long elivretId) {
        if (!elivretService.existsById(elivretId)) {
            throw new RuntimeException("Not found Elivret with id = " + elivretId);
        }
        List<Section> sections = sectionRepository.findByElivretId(elivretId);
        return sections;
    }

    public Section getSectionById(Long sectionId) {
        Section section = sectionRepository.findById(sectionId).orElseThrow(() -> new RuntimeException("cannot find section with id : " + sectionId));
        return section;
    }
}
