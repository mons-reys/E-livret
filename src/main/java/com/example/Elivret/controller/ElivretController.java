package com.example.Elivret.controller;


import com.example.Elivret.model.Elivret;
import com.example.Elivret.service.ElivretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = "token")
public class ElivretController {

    @Autowired
    private ElivretService elivretService;

    @PostMapping ("/add")
    public ResponseEntity createPost(@RequestBody Elivret elivret) {
        elivretService.createElivret(elivret);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Elivret>> showAllPosts() {
        return new ResponseEntity<>(elivretService.showAllElivrets(), HttpStatus.OK);
    }


}