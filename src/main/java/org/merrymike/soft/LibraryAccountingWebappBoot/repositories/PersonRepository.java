package org.merrymike.soft.LibraryAccountingWebappBoot.repositories;
/*
 * Copyright (c) 2024.
 * Created by Mykhailo Kovalenko
 */

import org.merrymike.soft.LibraryAccountingWebappBoot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
