package ru.denis.LibraryProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.denis.LibraryProject.models.Person;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> { //Integer - тип первичного ключа сущности Person
    Optional<Object> findByName(String name);

}
