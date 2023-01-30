package org.kliverogen.springcourse.models;

import javax.validation.constraints.*;

public class Person {
    private int personId;
    @NotEmpty(message = "ФИО не должно быть пустым")
    @Size(min = 2, max = 30, message = "Длина ФИО должны быть от 2 до 30 символов")
    @Pattern(regexp = "^[A-ZА-ЯЁ][a-zA-Zа-яёА-ЯЁ]* [A-ZА-ЯЁ][a-zA-Zа-яёА-ЯЁ]* [A-ZА-ЯЁ][a-zA-Zа-яёА-ЯЁ]*$",
            message = "ФИО должно быть в формате: Иванов Иван Иванович (cyrillic or latin)")
    private String fullName;

    @Min(value = 0, message = "Год родждения должен быть больше или равен 0")
    private int yearOfBirth;

    public Person() {
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + personId +
                ", fullName='" + fullName + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                '}';
    }
}
