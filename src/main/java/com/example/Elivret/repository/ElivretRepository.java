package com.example.Elivret.repository;

import com.example.Elivret.model.Elivret;
import com.example.Elivret.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElivretRepository extends JpaRepository<Elivret, Long> {
    List<Elivret> findByPersonsId(Long personId);

}

