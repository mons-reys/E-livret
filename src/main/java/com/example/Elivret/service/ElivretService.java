package com.example.Elivret.service;



import com.example.Elivret.model.Elivret;
import com.example.Elivret.repository.ElivretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Configurable
@Transactional
public class ElivretService {
    @Autowired
    private ElivretRepository  elivretRepository;


    public void createElivret(Elivret elivret) {
        System.out.println("done");
        elivretRepository.save(elivret);
    }
}
