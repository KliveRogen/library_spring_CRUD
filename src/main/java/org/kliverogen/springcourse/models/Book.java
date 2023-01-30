package org.kliverogen.springcourse.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class Book {
    private int bookId;
    private Integer personId;
    @Size(min = 2, max = 40, message = "Длина названия книги должна быть от 2 до 40")
    private String bookName;
    @Size(min = 2, max = 40, message = "Длина имени автора должна быть от 2 до 40")
    private String authorName;
    @Min(value = 0, message = "Год публикации должен быть больше или равен 0")
    private int yearOfPublication;

    public Book() {
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookName='" + bookName + '\'' +
                ", authorName='" + authorName + '\'' +
                ", yearOfPublication=" + yearOfPublication +
                '}';
    }
}
