package com.crud.testexercise.controllers;

import com.crud.testexercise.entities.Person;
import com.crud.testexercise.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PersonController {

    private final PersonRepository personRepository;

    @Autowired
    public PersonController(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    @GetMapping("/add")
    public String add(Person person){
        return "add";
    }

    @PostMapping("/add")
    public String add(@Validated Person person, BindingResult result, Model model){
        if (result.hasErrors()){
            return "add";
        }
        personRepository.save(person);
        model.addAttribute("person", personRepository.findAll());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") long id, Model model){
        Person person = personRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("invalid user id:" + id));
        model.addAttribute("person", person);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") long id, @Validated Person person, BindingResult result, Model model){
        if (result.hasErrors()){
            person.setId(id);
        }

        personRepository.save(person);
        model.addAttribute("person", personRepository.findAll());
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model){
        Person person = personRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("invalid user id:" + id));
        personRepository.delete(person);
        model.addAttribute("person", personRepository.findAll());
        return "index";
    }
}
