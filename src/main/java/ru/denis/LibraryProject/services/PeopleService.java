package ru.denis.LibraryProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.denis.LibraryProject.models.Person;
import ru.denis.LibraryProject.repositories.PeopleRepository;


import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true) //аннотация будет работать на всех методах,
// кроме тех на которых указана такая же аннотация
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
 //   @Transactional(readOnly = true)
    public List<Person> findALl() {
        return peopleRepository.findAll();
    }
//    @Transactional(readOnly = true)
    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }
    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void save(Person person) {
//        person.setMood(Mood.CALM); //при создании настроение CALM
//        person.setCreatedAt(new Date()); //Заполним поле dateAt текущим временем
        peopleRepository.save(person);
    }
    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public Optional<Object> findByName(String name) {
        return peopleRepository.findByName(name);
    }
}
