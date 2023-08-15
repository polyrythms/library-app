package ru.denis.LibraryProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.denis.LibraryProject.dao.PersonDAO;
import ru.denis.LibraryProject.models.Person;
import ru.denis.LibraryProject.util.PersonValidator;


import javax.validation.Valid;


@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired //контроллер конструируется на экземпляре DAO
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        model.addAttribute("books", personDAO.borrowedBooks(id));
        return "people/show";
    }
    //метод возвращает html форму для создания нового человека
    @GetMapping("/new")
//для создания вручную объекта Person    public String newPerson(Model model) { //Model model -- объект передается thymeleaf
    //создание объекта Person c Помощью аннотации @ModelAttribute
    public String newPerson(@ModelAttribute("person") Person person) {
        //создается пустой объект Person
        // и передается для thymeleaf шаблона в атрибут "object th:object="${person}""
//для создания вручную объекта Person        model.addAttribute("person", new Person());
        return "people/new"; //название thymeleaf шаблона в котором будет лежать форма запроса
    }
    //метод принимает на вход post запрос с параметрами (данные из формы), и добавляет нового человека в бд
    @PostMapping()
    //@ModelAttribute -- создает новый Person и в него положить параметры из формы
    //На этапе вложения параметров из html формы в объект Person @Valid проверяет данные на корректность
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/new"; //если при ввооде были ошибки, вернуть форму ввода
        personDAO.save(person);

        return "redirect:/people"; //redirect - ключевое слово для перехода браузером на другую страницу
    }
    @GetMapping("/{id}/edit") //при гет запросе по адесу конкретного id/edit
    public String edit(Model model, @PathVariable("id") int id) { //параметром внедряем модель и id,используемый в адресе запроса
        //поместим в модель текущего человека
        // (на форме редактирования будут отобрадаться его поля)
        model.addAttribute("person", personDAO.show(id));
        return "people/edit"; //адрес представления, в котором доступна наша модель
    }
//метод, который принимает PATCH запрос с адреса /people/id
    @PatchMapping("/{id}")
//    @ModelAttribute("person") Person person
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult, @PathVariable("id") int id) {
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "people/edit";
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}") //метод принимает из адреса id человека
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }

}
