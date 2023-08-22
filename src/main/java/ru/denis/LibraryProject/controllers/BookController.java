package ru.denis.LibraryProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.denis.LibraryProject.models.Book;
import ru.denis.LibraryProject.models.Person;
import ru.denis.LibraryProject.services.BooksService;
import ru.denis.LibraryProject.services.PeopleService;
import ru.denis.LibraryProject.util.BookValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

    private final PeopleService peopleService;
    private final BooksService booksService;
    private final BookValidator bookValidator;
    @Autowired
    public BookController(PeopleService peopleService, BooksService booksService, BookValidator bookValidator) {
        this.peopleService = peopleService;
        this.booksService = booksService;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(@RequestParam(value = "books_per_page", defaultValue = "0") int booksPerPage,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "sort_by_year", defaultValue = "false") boolean sort,
                        Model model) {
        if(booksPerPage == 0 & sort == false) {
            model.addAttribute("books", booksService.findAll());
        }else if (booksPerPage == 0 & sort == true){
            model.addAttribute("books", booksService.findAll(Sort.by("year")));
        } else if (booksPerPage != 0 & sort == false) {
            model.addAttribute("books", booksService.findAll(PageRequest.of(page, booksPerPage)).getContent());
        } else if (booksPerPage != 0 & sort == true) {
            model.addAttribute("books", booksService.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent());
        }
        return "books/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        Book book = booksService.findOne(id);
        model.addAttribute("book", booksService.findOne(id));
        Person owner = book.getOwner();
        if (owner != null) {
            model.addAttribute("owner", owner);
        } else {
            model.addAttribute("people", peopleService.findALl());
        }
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @GetMapping("/search")
    public String showSearch(@RequestParam(value = "partOfTitle", required = false) String partOfTitle,
                         Model model) {
        if(partOfTitle != null && partOfTitle.length() != 0)
            model.addAttribute("books", booksService.findByTitleStartingWith(partOfTitle));
        return "books/search";
    }
//    @GetMapping("/search")
//    public String search(@ModelAttribute("partOfTitle") String partOfTitle, Model model) {
//        if(partOfTitle != null && partOfTitle.length() != 0)
//            model.addAttribute("books", booksService.findByTitleStartingWith(partOfTitle));
//        else
//            model.addAttribute("books", null);
//        return "books/search";
//    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors())
            return "books/new"; //если при ввооде были ошибки, вернуть форму ввода
        booksService.save(book);

        return "redirect:/books";
    }
    @GetMapping("/{id}/edit") //при гет запросе по адесу конкретного id/edit
    public String edit(Model model, @PathVariable("id") int id) { //параметром внедряем модель и id,используемый в адресе запроса
        //поместим в модель текущую книгу
        // (на форме редактирования будут отображаться ее поля)
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit"; //адрес представления, в котором доступна наша модель
    }
    //метод, который принимает PATCH запрос с адреса /book/id
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int id) {
//        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors())
            return "books/edit";
        booksService.update(id, book);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        booksService.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }
    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        booksService.release(id);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }
}
