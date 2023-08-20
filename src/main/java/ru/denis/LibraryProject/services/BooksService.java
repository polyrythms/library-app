package ru.denis.LibraryProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.denis.LibraryProject.models.Book;
import ru.denis.LibraryProject.models.Person;
import ru.denis.LibraryProject.repositories.BooksRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public Optional<Book> findByTitle(String title) {
        return booksRepository.findByTitle(title);
    }
    public List<Book> findByOwner(Person owner) {
        return booksRepository.findByOwner(owner);
    }

    public List<Book> findByOwnerId(int id) {
        return booksRepository.findByOwnerId(id);
    }


    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }
    @Transactional
    public void assign(int id, Person selectedPerson) {
        Book assignedBook = findOne(id);
        assignedBook.setOwner(selectedPerson);
        List<Book> books = selectedPerson.getBooks();
        if (books != null) {
            books.add(assignedBook);
        } else {
            books = new ArrayList<Book>();
            books.add(assignedBook);
        }

    }
    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        booksRepository.findById(id).ifPresent(b -> {
            b.setAuthor(book.getAuthor());
            b.setTitle(book.getTitle());
            b.setYear(book.getYear());
        });
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void release(int id) {
        booksRepository.findById(id).ifPresent(book -> book.setOwner(null));
    }
}