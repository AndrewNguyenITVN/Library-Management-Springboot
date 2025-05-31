package com.library.LibraryManagement.entity;

import jakarta.persistence.*;

@Entity(name = "book_author_mapping")
public class BookAuthorMapping {
    @EmbeddedId
    private BookAuthorMappingId id;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book bookId;

    @ManyToOne
    @MapsId("authorId")
    @JoinColumn(name = "author_id")
    private BookAuthor authorId;

    public BookAuthorMappingId getId() {
        return id;
    }

    public void setId(BookAuthorMappingId id) {
        this.id = id;
    }

    public Book getBookId() {
        return bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
    }

    public BookAuthor getAuthorId() {
        return authorId;
    }

    public void setAuthorId(BookAuthor authorId) {
        this.authorId = authorId;
    }
}

@Embeddable
class BookAuthorMappingId implements java.io.Serializable {
    private int bookId;
    private int authorId;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookAuthorMappingId that = (BookAuthorMappingId) o;
        return bookId == that.bookId && authorId == that.authorId;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + bookId;
        result = 31 * result + authorId;
        return result;
    }
} 