package com.example.Elivret.repository;

import com.example.Elivret.model.Elivret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElivretRepository extends JpaRepository<Elivret, Long> {

}

