package com.example.assetmanagement.service.impl;

import com.example.assetmanagement.exception.PersonNotFoundException;
import com.example.assetmanagement.model.Person;
import com.example.assetmanagement.repository.AssetRepository;
import com.example.assetmanagement.repository.PersonRepository;
import com.example.assetmanagement.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Cacheable(value = "persons", condition = "#result != null")
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Cacheable(value = "person", key = "#id", condition = "#result != null")
    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    @Caching(evict = {
            @CacheEvict(value = "persons", allEntries = true),
            @CacheEvict(value = "person", key = "#person.id")
    })
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Caching(evict = {
            @CacheEvict(value = "persons", allEntries = true),
            @CacheEvict(value = "person", key = "#person.id")
    })
    public void deleteById(Long id) {
        assetRepository.deleteByPersonId(id);
        personRepository.deleteById(id);
    }

    @Override
    public void checkPerson(Long personId) {
        if(!personRepository.existsById(personId))
            throw new PersonNotFoundException();
    }
}
