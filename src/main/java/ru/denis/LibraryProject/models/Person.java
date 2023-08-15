package ru.denis.LibraryProject.models;

import javax.validation.constraints.*;


public class Person {
    private int id;
    @NotEmpty(message = "Name should not be empty") //валидация формы name для запрета на пустой ввод
    @Size(min = 2, max = 40, message = "Name should be between 2 and 30 characters") //ограничения на длинну строки
    private String name;
    @Min(value = 1900, message = "Age should be greater than zero")
    @Max(value = 2500, message = "Too old!")
    private int yearOfBirth;

    public Person() {

    }

    public Person(int id, String name, int yearOfBirth) {
        this.id = id;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
