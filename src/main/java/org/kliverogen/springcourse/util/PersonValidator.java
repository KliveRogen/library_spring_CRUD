package org.kliverogen.springcourse.util;

import org.kliverogen.springcourse.dao.PersonDAO;
import org.kliverogen.springcourse.models.Book;
import org.kliverogen.springcourse.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        //проверить, если чел с такой же фамилией в бд
        if(personDAO.show(((Person) target).getFullName()).isPresent()){
            errors.rejectValue("fullName", "", "This fullName is already taken");
        }
    }
}
