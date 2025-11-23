package books.controller;

import books.integration.JmsSender;
import books.model.Book;
import books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private JmsSender jmsSender;

    // Add book
    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        bookService.addBook(book);
        jmsSender.sendMessage(book);
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    // Update book
    @PutMapping("/{isbn}")
    public ResponseEntity<?> updateBook(@PathVariable String isbn, @RequestBody Book updatedBook) {
        bookService.updateBook(updatedBook);
        jmsSender.sendMessage(updatedBook);
        return new ResponseEntity<Book>(updatedBook, HttpStatus.OK);
    }

    // Get all books
    @GetMapping
    public ResponseEntity<?> getAllBooks() {
        Books books = new Books(bookService.getAllBooks());
        return new ResponseEntity<Books>(books, HttpStatus.OK);
    }

    // Get book by isbn
    @GetMapping("/{isbn}")
    public ResponseEntity<?> getBookById(@PathVariable String isbn) {
        Book book = bookService.getBook(isbn);
        if (book == null) {
            return new ResponseEntity<BookErrorType>(new BookErrorType("Book with isbn= "
                    + isbn + " is not available"), HttpStatus.NOT_FOUND);
        }
        jmsSender.sendMessage(book);
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    // Delete book
    @DeleteMapping("/{isbn}")
    public ResponseEntity<?> deleteBook(@PathVariable String isbn) {
        Book book = bookService.getBook(isbn);
        if (book == null) {
            return new ResponseEntity<BookErrorType>(new BookErrorType("Book with isbn= " + isbn + " is not available"),HttpStatus.NOT_FOUND);
        }
        bookService.deleteBook(isbn);
        jmsSender.sendMessage(book);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}