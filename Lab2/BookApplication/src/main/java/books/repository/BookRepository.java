package books.repository;

import books.model.Book;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BookRepository {
    private Map<String, Book> bookStore = new HashMap<>();

    public void addBook(Book book) {
        bookStore.put(book.getIsbn(), book);
    }

    public void updateBook(Book book) {
        bookStore.put(book.getIsbn(), book);
    }

    public void deleteBook(String isbn) {
        bookStore.remove(isbn);
    }

    public Book getBook(String isbn) {
        return bookStore.get(isbn);
    }

    public Collection<Book> findAllBooks() {
        return bookStore.values();
    }
}
