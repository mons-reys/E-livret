package com.example.Elivret.repository;

import com.example.Elivret.model.Comment;
import com.example.Elivret.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}

