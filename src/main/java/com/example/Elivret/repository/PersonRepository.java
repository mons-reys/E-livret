package com.example.Elivret.repository;

import com.example.Elivret.model.Person;
import com.example.Elivret.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}

