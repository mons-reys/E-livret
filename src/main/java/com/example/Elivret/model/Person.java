package com.example.Elivret.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Inheritance
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @NotNull
    private String firstName;

    @Basic
    @NotNull
    private String lastName;

    @Basic
    @NotNull
    private String email;

    @Basic
    @NotNull
    private String role;
}
