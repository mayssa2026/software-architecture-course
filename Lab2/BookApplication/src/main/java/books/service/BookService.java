package books.service;

import books.model.Book;
import books.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public void addBook(Book book) {
        bookRepository.addBook(book);
    }

    public void updateBook(Book book) {
        bookRepository.updateBook(book);
    }

    public void deleteBook(String isbn) {
        bookRepository.deleteBook(isbn);
    }

    public Book getBook(String isbn) {
        return bookRepository.getBook(isbn);
    }

    public Collection<Book> getAllBooks() {
        return bookRepository.findAllBooks();
    }
}
