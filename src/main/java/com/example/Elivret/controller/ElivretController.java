package com.example.Elivret.controller;


import com.example.Elivret.model.Elivret;
import com.example.Elivret.service.ElivretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ElivretController {

    @Autowired
    private ElivretService elivretService;

    @PostMapping
    public ResponseEntity createElivret(@RequestBody Elivret elivret) {
        elivretService.createElivret(elivret);
        return new ResponseEntity(HttpStatus.OK);
    }


}