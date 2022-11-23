package com.example.Elivret;

import com.example.Elivret.model.Elivret;
import com.example.Elivret.service.ElivretService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class ElivretApplicationTest {
    @Autowired
    ElivretService elivretService;

    @Test
    public void testCreateElivret(){
        Elivret p = new Elivret();
        p.setTitle("E2");

        elivretService.createElivret(p);
        assertEquals("E2", p.getTitle());
    }


    @Test
    public void testShowElivret(){
        List<Elivret> list =  elivretService.showAllPosts();
        assertEquals("E2", list.get(0).getTitle());
    }
}