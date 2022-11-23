package com.example.Elivret.repository;

import com.example.Elivret.model.Elivret;
import com.example.Elivret.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

}

