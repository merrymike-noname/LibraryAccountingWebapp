package org.merrymike.soft.LibraryAccountingWebappBoot.models;
/*
 * Copyright (c) 2024.
 * Created by Mykhailo Kovalenko
 */

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Book name should not be empty")
    @Size(min = 1, max = 50, message = "Book name should be between 1 and 50 characters")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Author name should not be empty")
    @Size(min = 2, max = 40, message = "Author name should be between 2 and 40 characters")
    @Column(name = "author")
    private String author;

    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    @Transient
    private boolean isOverdue;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
        isOverdue = false;
    }

    public Book() {
        isOverdue = false;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public boolean isOverdue() {
        return isOverdue;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title +
                ", author='" + author +
                ", year=" + year +
                '}';
    }
}
