package ru.denis.LibraryProject.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.denis.LibraryProject.models.Person;
import ru.denis.LibraryProject.services.PeopleService;


@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;
    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
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
        if (peopleService.findByName(person.getName()).isPresent()) { //если человек с таким именем существует
            errors.rejectValue("name", "", "This name is already taken"); //"поле", "код ошибки", "текст ошибки"
        }
    }
}
