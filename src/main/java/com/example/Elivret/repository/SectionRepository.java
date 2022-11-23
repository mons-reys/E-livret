package com.example.Elivret.repository;

import com.example.Elivret.model.Question;
import com.example.Elivret.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findByElivretId(Long postId);
}

