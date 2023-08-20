//package ru.denis.LibraryProject.dao;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//import ru.denis.LibraryProject.models.Book;
//import ru.denis.LibraryProject.models.Person;
//
//import java.util.List;
//import java.util.Optional;
//
//
//@Component
//public class PersonDAO {
//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public PersonDAO(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public List<Person> index() {
//        //RowMapper - объект, ктороый обтображает объекты строки таблицы в поля нашей сущности Person
//        //можно не делать кастомный RowMapper, вместо этого можно воспользоваться готовым маппером
//        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
//    }
//    public Optional<Person> show(String name) {
//        return jdbcTemplate.query
//                ("SELECT * FROM Person WHERE name=?", new Object[] {name}, //выбрать строки с email из запроса
//                        new BeanPropertyRowMapper<>(Person.class)). //создаем человека на основании запроса в бд
//                stream().findAny(); //прогонаем стримом результаты, если не пусты возвращаем человека
//    }
//
//    public Person show(int id) {
//        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).
//                stream().findAny().orElse(null);
//    }
//    public void save(Person person) {
//        //метод update принимает вторым аргументом массив объектов
//        jdbcTemplate.update("INSERT INTO Person(name, yearofbirth) VALUES(?, ?)",
//                person.getName(), person.getYearOfBirth());
//    }
//    public void update(int id, Person updatedPerson) {
//        jdbcTemplate.update("UPDATE Person SET name=?, yearofbirth=? WHERE id=?",
//                updatedPerson.getName(), updatedPerson.getYearOfBirth(), id);
//    }
//    public void delete(int id) {
//        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
//    }
//    public List<Book> borrowedBooks(int id) {
//        return jdbcTemplate.query("SELECT book.* FROM book WHERE book.person_id=?", new Object[] {id},
//                new BeanPropertyRowMapper<>(Book.class));
//    }
//
//}
