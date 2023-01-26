package com.example.Elivret.service;



import com.example.Elivret.model.Elivret;
import com.example.Elivret.model.Person;
import com.example.Elivret.repository.ElivretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Configurable
@Transactional
public class ElivretService {
    @Autowired
    private ElivretRepository  elivretRepository;
    
    public void updateElivret(Long elivretId,String title){
        Elivret elivretToUpdate = elivretRepository.findById(elivretId).orElseThrow(() -> new RuntimeException("cannot find eLivret with id : " + elivretId));
        elivretToUpdate.setTitle(title);
        elivretRepository.save(elivretToUpdate);
    }


    public List<Elivret> getElivretByPersonId(Long personId) {
        List<Elivret>  elivrets = elivretRepository.findAll();
        List<Elivret> result = new ArrayList<Elivret>();
        for (int i = 0; i < elivrets.size(); i++){
            List<Person> persons = elivrets.get(i).getPersons();
            for (int j = 0; j < persons.size(); j++){
                if( persons.get(j).getId() == personId ){
                    result.add(elivrets.get(i));
                }
            }
        }

        return result;
    }
    
    public Elivret getElivretById(Long elivretId) {
        Elivret elivret = elivretRepository.findById(elivretId).orElseThrow(() -> new RuntimeException("cannot find eLivret with id : " + elivretId));
        return elivret;
    }

    public void createElivret(Elivret elivret) {
        elivretRepository.save(elivret);
    }

    public List<Elivret> findAllElivrets() {
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
