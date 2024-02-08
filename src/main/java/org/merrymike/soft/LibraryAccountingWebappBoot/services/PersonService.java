package org.merrymike.soft.LibraryAccountingWebappBoot.services;
/*
 * Copyright (c) 2024.
 * Created by Mykhailo Kovalenko
 */


import org.merrymike.soft.LibraryAccountingWebappBoot.models.Book;
import org.merrymike.soft.LibraryAccountingWebappBoot.models.Person;
import org.merrymike.soft.LibraryAccountingWebappBoot.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll(int pageId, int itemsPerPage) {
        return personRepository.findAll(PageRequest.of(pageId, itemsPerPage)).getContent();
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(int id) {
        Optional<Person> foundPerson = personRepository.findById(id);
        return foundPerson.orElse(null);
    }

    public List<Book> getBooks(int id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            List<Book> books = person.get().getBooks();
            Instant currentDate = Instant.now();
            for (Book book : books) {
                Instant takenAt = book.getTakenAt().toInstant();
                Duration duration = Duration.between(takenAt, currentDate);
                if (duration.toDays() > 15) book.setOverdue(true);
            }
            return person.get().getBooks();
        } else {
            return Collections.emptyList();
        }
    }

    public void save(Person person) {
        personRepository.save(person);
    }

    public void update(int id, Person updatedPerson) {
        if (personRepository.existsById(id)) {
            updatedPerson.setId(id);
            personRepository.save(updatedPerson);
        }
    }

    public void delete(Person person) {
        personRepository.delete(person);
    }

    public void deleteById(int id) {
        personRepository.deleteById(id);
    }
}
