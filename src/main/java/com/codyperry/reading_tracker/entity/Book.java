package com.codyperry.reading_tracker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "author", nullable = false, length = 50)
    private String author;

    @Column(name = "pages", nullable = false)
    private Integer pages;

    @Column(name = "pages_read")
    private Integer pagesRead = 0;

    @Transient
    private Double percentComplete;

    public Integer getPagesRead() {
        return pagesRead;
    }

    public void setPagesRead(Integer pagesRead) {
        if (pagesRead < 0) {
            pagesRead = 0;
        }

        if (pagesRead > this.pages) {
            pagesRead = this.pages;
        }

        this.pagesRead = pagesRead;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Double getPercentComplete() {
        return percentComplete;
    }

    public void setPercentComplete(Double percentComplete) {
        if (percentComplete < 0.0) {
            this.percentComplete = 0.0;
        }

        if (percentComplete > 1.0) {
            this.percentComplete = 1.0;
        }

        this.percentComplete = percentComplete;
    }
}
