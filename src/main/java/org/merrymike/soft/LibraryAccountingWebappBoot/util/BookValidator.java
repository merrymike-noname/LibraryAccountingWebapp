package org.merrymike.soft.LibraryAccountingWebappBoot.util;
/*
 * Copyright (c) 2024.
 * Created by Mykhailo Kovalenko
 */

import org.merrymike.soft.LibraryAccountingWebappBoot.models.Book;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {
    @Override
    public boolean supports(Class<?> classToCheck) {
        return Book.class.equals(classToCheck);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if (book.getYear() > java.time.LocalDate.now().getYear()) {
            errors.rejectValue("year", "", "Year should not be greater than current");
        }
    }
}
