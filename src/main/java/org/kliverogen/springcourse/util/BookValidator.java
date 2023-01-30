package org.kliverogen.springcourse.util;

import org.kliverogen.springcourse.dao.BookDAO;
import org.kliverogen.springcourse.dao.PersonDAO;
import org.kliverogen.springcourse.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class BookValidator implements Validator {
    private BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if(!bookDAO.show(book.getAuthorName()).isEmpty()){
            errors.rejectValue("personId", "", "Нет такого человека");
        }


        List<RacerResult> dsaas = new ArrayList<>();
        dsaas.add(new RacerResult("0", List.of(LocalDate.now(), LocalDate.MIN, LocalDate.MAX)));
        dsaas.add(new RacerResult("1", List.of(LocalDate.now(), LocalDate.MIN, LocalDate.MAX)));
        dsaas.add(new RacerResult("2", List.of(LocalDate.now(), LocalDate.MIN, LocalDate.MAX)));
        dsaas.add(new RacerResult("3", List.of(LocalDate.now(), LocalDate.MIN, LocalDate.MAX)));

        Map<String, LocalDate> fastMap = dsaas.stream().collect(Collectors.toMap(RacerResult::getName,
                racerResult -> racerResult.getDates().stream().min(LocalDate::compareTo).get(),
                (o, o2) -> {throw new RuntimeException();},
                () -> new TreeMap<>()
        ));
    }

    private static class RacerResult{
        private String name;
        private List<LocalDate> dates;

        public RacerResult(String name, List<LocalDate> dates) {
            this.name = name;
            this.dates = dates;
        }

        public String getName() {
            return name;
        }

        public List<LocalDate> getDates() {
            return dates;
        }
    }
}
