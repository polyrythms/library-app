package ru.denis.LibraryProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.denis.LibraryProject.dao.BookDAO;
import ru.denis.LibraryProject.dao.PersonDAO;
import ru.denis.LibraryProject.models.Book;
import ru.denis.LibraryProject.models.Person;
import ru.denis.LibraryProject.util.BookValidator;


import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final BookValidator bookValidator;
    private final PersonDAO personDAO;

    @Autowired //контроллер конструируется на экземпляре DAO
    public BookController(BookDAO bookDAO, BookValidator bookValidator, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
        this.personDAO = personDAO;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.show(id));
        Optional<Person> owner = bookDAO.getBookOwner(id);
        if (owner.isPresent()) {
            model.addAttribute("owner", owner.get());
        } else {
            model.addAttribute("people", personDAO.index());
        }
        return "books/show";
    }
    //метод возвращает html форму для создания новой книги
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        //создается пустой объект Book
        // и передается для thymeleaf шаблона в атрибут "object th:object="${book}""
        //для создания вручную объекта Person        model.addAttribute("book", new Book());
        return "books/new"; //название thymeleaf шаблона в котором будет лежать форма запроса
    }
    //метод принимает на вход post запрос с параметрами (данные из формы) и добавляет новую книгу в бд
    @PostMapping()
    //@ModelAttribute -- создает новый Book и в него положить параметры из формы
    //На этапе вложения параметров из html формы в объект Book @Valid проверяет данные на корректность
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors())
            return "books/new"; //если при ввооде были ошибки, вернуть форму ввода
        bookDAO.save(book);

        return "redirect:/books"; //redirect - ключевое слово для перехода браузером на другую страницу
    }
    @GetMapping("/{id}/edit") //при гет запросе по адесу конкретного id/edit
    public String edit(Model model, @PathVariable("id") int id) { //параметром внедряем модель и id,используемый в адресе запроса
        //поместим в модель текущую книгу
        // (на форме редактирования будут отображаться ее поля)
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit"; //адрес представления, в котором доступна наша модель
    }
    //метод, который принимает PATCH запрос с адреса /book/id
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int id) {
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors())
            return "books/edit";
        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        bookDAO.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }
    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookDAO.release(id);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }
}
