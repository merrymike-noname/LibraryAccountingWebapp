package org.merrymike.soft.LibraryAccountingWebappBoot.repositories;
/*
 * Copyright (c) 2024.
 * Created by Mykhailo Kovalenko
 */

import org.merrymike.soft.LibraryAccountingWebappBoot.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByTitleStartingWithIgnoreCase(String searchFor);
}
