package org.merrymike.soft.LibraryAccountingWebappBoot.controllers;
/*
 * Copyright (c) 2024.
 * Created by Mykhailo Kovalenko
 */

import jakarta.validation.Valid;
import org.merrymike.soft.LibraryAccountingWebappBoot.models.Book;
import org.merrymike.soft.LibraryAccountingWebappBoot.models.Person;
import org.merrymike.soft.LibraryAccountingWebappBoot.services.BookService;
import org.merrymike.soft.LibraryAccountingWebappBoot.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookService bookService;
    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BookService bookService, BookValidator bookValidator) {
        this.bookService = bookService;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") int page,
                        @RequestParam(name = "books_per_page", defaultValue = "6") int booksPerPage,
                        @RequestParam(name = "sort_by", defaultValue = "default") String sortBy) {

        List<String> sortOptions = Arrays.asList("default", "title", "author", "year");
        Page<Book> bookPage = bookService.findAll(page - 1, booksPerPage, sortBy);

        model.addAttribute("bookPage", bookPage);
        model.addAttribute("page", page);
        model.addAttribute("booksPerPage", booksPerPage);
        model.addAttribute("sortOptions", sortOptions);
        model.addAttribute("selectedSort", sortBy);
        model.addAttribute("pageNumbers", bookService.getPageNumbers(bookPage.getTotalPages()));

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("newOwner") Person person) {
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("owner", bookService.getOwner(id));
        model.addAttribute("people", bookService.getAllPeople());
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) return "books/new";
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int id) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) return "books/edit";
        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/setOwner")
    public String setOwner(@PathVariable("id") int id, @ModelAttribute("newOwner") Person person) {
        bookService.setOwner(id, person);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/unsetOwner")
    public String unsetOwner(@PathVariable("id") int id) {
        bookService.unsetOwner(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "search_for", required = false) String searchFor, Model model) {
        model.addAttribute("isSearched", false);
        if (searchFor != null && !searchFor.isEmpty()) {
            model.addAttribute("searchResults", bookService.findByTitle(searchFor));
            model.addAttribute("isSearched", true);
        }
        return "books/search";
    }
}
