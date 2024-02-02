package org.merrymike.soft.LibraryAccountingWebappBoot.util;
/*
 * Copyright (c) 2024.
 * Created by Mykhailo Kovalenko
 */

import org.merrymike.soft.LibraryAccountingWebappBoot.models.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class PersonValidator implements Validator {
    @Override
    public boolean supports(Class<?> classToCheck) {
        return Person.class.equals(classToCheck);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        LocalDate birthDate = person.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (birthDate.getYear() < 1900 || birthDate.isAfter(LocalDate.now())) {
            errors.rejectValue("birthYear", "", "Birth year should be valid");
        }
    }
}
