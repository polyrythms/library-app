package ru.denis.LibraryProject.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.denis.LibraryProject.models.Book;
import ru.denis.LibraryProject.services.BooksService;


@Component //внедрим personDAO спрингом
public class BookValidator implements Validator {
    private final BooksService booksService;

    @Autowired
    public BookValidator(BooksService booksService) {
        this.booksService = booksService;
    }

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
        if (booksService.findByTitle(book.getTitle()).isPresent()) { //если книга с таким названием уже  существует
            errors.rejectValue("title", "", "This title is already taken"); //"поле", "код ошибки", "текст ошибки"
        }
    }
}
