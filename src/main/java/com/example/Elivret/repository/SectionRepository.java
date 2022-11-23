package com.example.Elivret.repository;

import com.example.Elivret.model.Question;
import com.example.Elivret.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

}

