package com.example.assetmanagement.controller;

import com.example.assetmanagement.controller.request.PersonParam;
import com.example.assetmanagement.controller.response.RestResponse;
import com.example.assetmanagement.model.Person;
import com.example.assetmanagement.service.PersonService;
import com.example.assetmanagement.utils.GsonMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    @Operation(summary = "Get All Persons", description = "取得所有使用者列表")
    public RestResponse<List<Person>> getAllPersons() {
        return new RestResponse<>(personService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Person By Id", description = "取得指定使用者資料")
    public RestResponse<Optional<Person>> getPersonById(@PathVariable Long id) {
        return new RestResponse<>(personService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create Person", description = "建立使用者")
    public RestResponse<Person> createPerson(@RequestBody PersonParam param) {
        Person person = GsonMapper.convert(param, Person.class);
        return new RestResponse<>(personService.save(person));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Person", description = "修改使用者資料")
    public RestResponse<Person> updatePerson(@PathVariable Long id, @RequestBody PersonParam param) {
        Person person = GsonMapper.convert(param, Person.class);
        person.setId(id);
        return new RestResponse<>(personService.save(person));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Person", description = "刪除使用者")
    public RestResponse<?> deletePerson(@PathVariable Long id) {
        personService.deleteById(id);
        return new RestResponse<>();
    }
}
