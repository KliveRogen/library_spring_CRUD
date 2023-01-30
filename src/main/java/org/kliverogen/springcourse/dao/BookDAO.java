package org.kliverogen.springcourse.dao;

import org.kliverogen.springcourse.models.Book;
import org.kliverogen.springcourse.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {

        return jdbcTemplate.query("SELECT * FROM book",
                new BeanPropertyRowMapper<>(Book.class)
        );
    }

    public Optional<Book> show(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE book_id=?", new BeanPropertyRowMapper<>(Book.class), id)
                .stream()
                .findAny();
    }

    public Optional<Book> show(String authorName) {
        return jdbcTemplate.query("SELECT * FROM book WHERE author_name=?", new BeanPropertyRowMapper<>(Book.class), authorName)
                .stream()
                .findAny();
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(person_id, book_name, author_name, year_of_publication) VALUES(?, ?, ?, ?)",
                book.getPersonId(),
                book.getBookName(),
                book.getAuthorName(),
                book.getYearOfPublication()
        );

    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE book SET person_id=?, book_name=?, author_name=?, year_of_publication=? WHERE book_id=?",
                updatedBook.getPersonId(),
                updatedBook.getBookName(),
                updatedBook.getAuthorName(),
                updatedBook.getYearOfPublication(),
                id
        );

    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE book_id=?",
                id
        );
    }

    public List<Book> findPersonBooks(int personId) {
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id=?",
                new BeanPropertyRowMapper<>(Book.class),
                personId
        );
    }

    public Optional<Person> findBookOwner(int bookId) {
        Optional<Person> person = jdbcTemplate.query("SELECT * from person WHERE person_id = (SELECT (person_id) from book WHERE book_id = ?)",
                        new BeanPropertyRowMapper<>(Person.class),
                        bookId
                )
                .stream()
                .findAny();
        System.out.println(person);
        return person;
    }

    public void releaseBook(int bookId) {
        jdbcTemplate.update("UPDATE book SET person_id=NULL WHERE book_id=?", bookId);
    }

    public void assignBook(int bookId, int personId) {
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE book_id=?",
                personId,
                bookId
        );
    }
}
