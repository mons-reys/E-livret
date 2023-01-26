package com.example.Elivret.repository;

import com.example.Elivret.model.Person;
import com.example.Elivret.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByUserName(String username);

    Person findByEmail(String username);
    boolean existsByUserName(String username);

    boolean existsByEmail(String username);

    @Transactional
    void deleteByUserName(String username);

}

