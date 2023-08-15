package ru.denis.LibraryProject.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.denis.LibraryProject.models.Book;
import ru.denis.LibraryProject.models.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        //RowMapper - объект, ктороый обтображает объекты строки таблицы в поля нашей сущности
        //можно не делать кастомный RowMapper, вместо этого можно воспользоваться готовым маппером
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }
    public Optional<Book> show(String title) {
        return jdbcTemplate.query
                ("SELECT * FROM Book WHERE title=?", new Object[] {title},
                        new BeanPropertyRowMapper<>(Book.class)).
                stream().findAny();
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).
                stream().findAny().orElse(null);
    }
//    public List<Book> showFree() {
//        return jdbcTemplate.query("SELECT FROM * Book WHERE person_id=?",
//                new Object[]{null}, new BeanPropertyRowMapper<>(Book.class));
//    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(title, author, year) VALUES(?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }
    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET title=?, author=?, year=? WHERE id=?",
                updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getYear(), id);
    }
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }
    public void release(int id) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE id=?",
                null, id);
    }
    public void assign(int id, Person person) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE id=?",
                person.getId(), id);
    }
    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query(
                "SELECT person.* FROM book JOIN person ON person.id=book.person_id WHERE book.id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

}
