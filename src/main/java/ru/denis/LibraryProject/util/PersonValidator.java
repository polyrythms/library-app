package ru.denis.LibraryProject.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.denis.LibraryProject.dao.PersonDAO;
import ru.denis.LibraryProject.models.Person;


@Component //внедрим personDAO спрингом
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;
    //конструктор для внедрения personDAO спрингом
    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }
    //реализуем методы интерфейса Validator
    @Override
    //метод ограничивает использование валидатора только для объектоа класса Person
    public boolean supports(Class<?> aClass) {
        //возвращает true если только aClass - Person
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        //кастим o к классу Person, выше проверка уже проведена
        Person person = (Person) o;
        //посмотреть, есть ли человек с таким же email-ом
        if (personDAO.show(person.getName()).isPresent()) { //если человек с таким имейлом существует
            errors.rejectValue("name", "", "This name is already taken"); //"поле", "код ошибки", "текст ошибки"
        }
    }
}
