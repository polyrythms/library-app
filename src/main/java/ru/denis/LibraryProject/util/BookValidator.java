package ru.denis.LibraryProject.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.denis.LibraryProject.dao.BookDAO;
import ru.denis.LibraryProject.models.Book;


@Component //внедрим personDAO спрингом
public class BookValidator implements Validator {
    private final BookDAO bookDAO;
    //конструктор для внедрения personDAO спрингом
    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }
    //реализуем методы интерфейса Validator
    @Override
    //метод ограничивает использование валидатора только для объектоа класса Person
    public boolean supports(Class<?> aClass) {
        //возвращает true если только aClass - Person
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        //кастим o к классу Person, выше проверка уже проведена
        Book book = (Book) o;
        //посмотреть, есть ли человек с таким же email-ом
        if (bookDAO.show(book.getTitle()).isPresent()) { //если человек с таким имейлом существует
            errors.rejectValue("title", "", "This title is already taken"); //"поле", "код ошибки", "текст ошибки"
        }
    }
}
