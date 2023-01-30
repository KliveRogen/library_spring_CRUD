package org.kliverogen.springcourse.controllers;

import org.kliverogen.springcourse.dao.BookDAO;
import org.kliverogen.springcourse.dao.PersonDAO;
import org.kliverogen.springcourse.models.Book;
import org.kliverogen.springcourse.models.Person;
import org.kliverogen.springcourse.util.BookValidator;
import org.kliverogen.springcourse.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private static final String REDIRECT_TO_PEOPLE = "redirect:/people";
    private final PersonDAO personDAO;
    private final BookDAO bookDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, BookDAO bookDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
        this.personValidator = personValidator;
    }



    @GetMapping()
    public String index(Model model) {
        //Получение всех людей из DAO
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model) {
        //получение одного человека из DAO и передача в представление
        model.addAttribute("person", personDAO.show(id).get());
        model.addAttribute("books", bookDAO.findPersonBooks(id));
        return "people/show";
    }


    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personDAO.save(person);
        return REDIRECT_TO_PEOPLE;
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable int id) {
        model.addAttribute("person", personDAO.show(id).get());
        return "people/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(Model model, @PathVariable int id) {
        personDAO.delete(id);
        return REDIRECT_TO_PEOPLE;
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult, @PathVariable int id) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        personDAO.update(id, person);
        return REDIRECT_TO_PEOPLE;
    }

}
