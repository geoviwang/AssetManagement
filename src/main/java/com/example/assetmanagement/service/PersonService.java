package com.example.assetmanagement.service;

import com.example.assetmanagement.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    List<Person> findAll();

    Optional<Person> findById(Long id);

    Person save(Person person);

    void deleteById(Long id);

    void checkPerson(Long personId);
}
