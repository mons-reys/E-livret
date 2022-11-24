package com.example.Elivret.service;



import com.example.Elivret.model.Elivret;
import com.example.Elivret.repository.ElivretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<Elivret> showAllElivrets() {
        List<Elivret> posts = elivretRepository.findAll();
        return posts;
    }

    public Elivret findElivretById(Long elivretId) {
        Elivret elivret = elivretRepository.findById(elivretId).orElseThrow(() -> new RuntimeException("cannot find elivret with id : " + elivretId));
        return elivret;
    }

    public boolean existsById(Long elivretId) {
        return elivretRepository.existsById(elivretId);
    }

    public void deleteById(long elivretId) {
        elivretRepository.deleteById(elivretId);
    }
}
