package ru.denis.LibraryProject.models;

import javax.validation.constraints.*;


public class Book {
    private int id;
    @NotEmpty(message = "Title should not be empty") //валидация формы name для запрета на пустой ввод
    @Size(min = 2, max = 100, message = "Title should be between 2 and 30 characters") //ограничения на длинну строки
    private String title;
    @NotEmpty(message = "Author name should not be empty") //валидация формы name для запрета на пустой ввод
    @Size(min = 2, max = 100, message = "Author name should be between 2 and 30 characters") //ограничения на длинну строки
    private String author;
    @Min(value = 0, message = "Age should be greater than zero")
    @Max(value = 2500, message = "Too old!")
    private int year;

    public Book() {

    }

    public Book(int id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
