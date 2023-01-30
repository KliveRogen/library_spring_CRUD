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
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private static final String REDIRECT_TO_BOOKS = "redirect:/books";
    private final PersonDAO personDAO;
    private final BookDAO bookDAO;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(PersonDAO personDAO, BookDAO bookDAO, BookValidator bookValidator) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }


    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        book.setPersonId(null);
        bookDAO.save(book);
        return REDIRECT_TO_BOOKS;
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable int id){
        model.addAttribute("book", bookDAO.show(id).get());
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable int id) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookDAO.update(id, book);
        return REDIRECT_TO_BOOKS;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id){
        bookDAO.delete(id);
        return REDIRECT_TO_BOOKS;
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable int id){
        model.addAttribute("book", bookDAO.show(id).get());
        Optional<Person> bookOwner = bookDAO.findBookOwner(id);
        if(bookOwner.isPresent()){
            model.addAttribute("owner", bookOwner.get());
        } else {
            model.addAttribute("people", personDAO.index());
            model.addAttribute("assignPerson", new Person());
        }
        return "books/show";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable int id){
        bookDAO.releaseBook(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable int id, @ModelAttribute("assignPerson") Person person){
        bookDAO.assignBook(id, person.getPersonId());
        return "redirect:/books/" + id;
    }
}
