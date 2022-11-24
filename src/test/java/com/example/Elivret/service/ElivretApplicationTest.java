package com.example.Elivret;

import com.example.Elivret.model.Elivret;
import com.example.Elivret.model.Section;
import com.example.Elivret.service.ElivretService;
import com.example.Elivret.service.SectionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class ElivretApplicationTest {
    @Autowired
    ElivretService elivretService;

    @Autowired
    SectionService sectionService;

    @Test
    public void testCreateAndGetAndDeleteElivret(){
        Elivret p = new Elivret();
        p.setTitle("E2");
        elivretService.createElivret(p);
        List<Elivret> list =  elivretService.showAllElivrets();
        assertEquals("E2", list.get(0).getTitle());
        assertEquals("E2", p.getTitle());
        System.out.println(p.getId());
        elivretService.deleteById(p.getId());
        assertEquals(null, elivretService.findElivretById(p.getId()));
    }

    @Test
    public void testCreateAndGetSeciton(){
        Elivret elivret = new Elivret();
        elivret.setTitle("E2");
        elivretService.createElivret(elivret);

        Section section1 = new Section();
        section1.setTitle("section Title 1");
        section1.setElivret(elivret);

        Section section2 = new Section();
        section2.setTitle("section Title 2");
        section2.setElivret(elivret);

        List<Section> sections = sectionService.findSectionsByElivretId(elivret.getId());

        System.out.println(sections);

        assertEquals("section Title 22", sections.get(1).getTitle());
        assertEquals("section Title", section1.getTitle());
    }

}