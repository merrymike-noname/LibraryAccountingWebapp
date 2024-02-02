package org.merrymike.soft.LibraryAccountingWebappBoot.services;
/*
 * Copyright (c) 2024.
 * Created by Mykhailo Kovalenko
 */

import org.merrymike.soft.LibraryAccountingWebappBoot.models.Book;
import org.merrymike.soft.LibraryAccountingWebappBoot.models.Person;
import org.merrymike.soft.LibraryAccountingWebappBoot.repositories.BookRepository;
import org.merrymike.soft.LibraryAccountingWebappBoot.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    public Page<Book> findAll(int page, int booksPerPage, String sortBy) {
        Sort sort = sortBy.equals("default") ? Sort.unsorted() : Sort.by(sortBy);
        Pageable pageable = PageRequest.of(page, booksPerPage, sort);
        return bookRepository.findAll(pageable);
    }

    public Book findById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

    public void update(int id, Book updatedBook) {
        if (bookRepository.existsById(id)) {
            updatedBook.setId(id);
            bookRepository.save(updatedBook);
        }
    }

    public void delete(Book book) {
        bookRepository.delete(book);
    }

    public void deleteById(int id) {
        bookRepository.deleteById(id);
    }

    public Person getOwner(int id) {
        return bookRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }

    public void setOwner(int id, Person owner) {
        if (bookRepository.findById(id).isPresent()) {
            Book book = bookRepository.findById(id).get();
            book.setOwner(owner);
            book.setTakenAt(new Date());
            System.out.println(book.getTakenAt());
        }
    }

    public void unsetOwner(int id) {
        if (bookRepository.findById(id).isPresent()) {
            Book book = bookRepository.findById(id).get();
            book.setOwner(null);
            book.setTakenAt(null);
        }
    }

    public List<Integer> getPageNumbers(int totalPages) {
        if (totalPages > 0) {
            return IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<Book> findByTitle(String searchBy) {
        return bookRepository.findByTitleStartingWithIgnoreCase(searchBy);
    }
}
