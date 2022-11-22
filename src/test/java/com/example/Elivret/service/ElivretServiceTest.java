package com.example.Elivret.service;

import com.example.Elivret.model.Elivret;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class ElivretServiceTest {

    @Autowired
    ElivretService elivretService;

    @Test
    void createElivret() {
        Elivret p = new Elivret();
        p.setTitle("E1");

        elivretService.createElivret(p);
        assertEquals("E1", p.getTitle());
    }
}