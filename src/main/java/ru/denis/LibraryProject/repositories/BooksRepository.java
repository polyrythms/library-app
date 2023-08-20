package ru.denis.LibraryProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.denis.LibraryProject.models.Book;
import ru.denis.LibraryProject.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

    Optional<Book> findByTitle(String title);

    List<Book> findByOwner(Person owner);

    List<Book> findByOwnerId(int id);
}
