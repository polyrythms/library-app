package ru.denis.LibraryProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.denis.LibraryProject.models.Person;
import ru.denis.LibraryProject.services.BooksService;
import ru.denis.LibraryProject.services.PeopleService;
import ru.denis.LibraryProject.util.PersonValidator;

import javax.validation.Valid;


@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final BooksService booksService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, BooksService booksService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.booksService = booksService;
        this.personValidator = personValidator;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleService.findALl());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findOne(id));
        model.addAttribute("books", booksService.findByOwnerId(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/new"; //если при ввооде были ошибки, вернуть форму ввода
        peopleService.save(person);

        return "redirect:/people"; //redirect - ключевое слово для перехода браузером на другую страницу
    }
    @GetMapping("/{id}/edit") //при гет запросе по адесу конкретного id/edit
    public String edit(Model model, @PathVariable("id") int id) { //параметром внедряем модель и id,используемый в адресе запроса
        //поместим в модель текущего человека
        // (на форме редактирования будут отобрадаться его поля)
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit"; //адрес представления, в котором доступна наша модель
    }
//метод, который принимает PATCH запрос с адреса /people/id
    @PatchMapping("/{id}")
//    @ModelAttribute("person") Person person
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult, @PathVariable("id") int id) {
//        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "people/edit";
        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}") //метод принимает из адреса id человека
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }

}
