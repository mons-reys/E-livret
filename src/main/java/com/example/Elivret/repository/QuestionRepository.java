package com.example.Elivret.repository;

import com.example.Elivret.model.Elivret;
import com.example.Elivret.model.Question;
import com.example.Elivret.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findBySectionId(Long postId);
}

