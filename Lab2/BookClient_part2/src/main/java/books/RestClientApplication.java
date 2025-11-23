package books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RestClientApplication implements CommandLineRunner {
	@Autowired
	private RestOperations restTemplate;

	public static void main(String[] args) {
		SpringApplication.run(RestClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        String serverUrl = "http://localhost:9090/books";

        // ---------------- Add Books ----------------
        restTemplate.postForLocation(serverUrl, new Book("9780134685991","Joshua Bloch", "Effective Java", 45.99));
        restTemplate.postForLocation(serverUrl, new Book("9780596009205","Kathy Sierra", "Head First Java", 35.50));

        // ---------------- Get One Book ----------------
        Book book = restTemplate.getForObject(serverUrl + "/{isbn}", Book.class, "9780134685991");
        System.out.println("----------- Get One Book -----------------------");
        assert book != null;
        System.out.println(book.getTitle() + " by " + book.getAuthor());

        // ---------------- Get All Books ----------------
        Books allBooks = restTemplate.getForObject(serverUrl, Books.class);
        assert allBooks != null;
        System.out.println(allBooks.getBooks());

        // ---------------- Update a Book ----------------
        book.setPrice(49.99);
        restTemplate.put(serverUrl + "/{isbn}", book, book.getIsbn());

        // ---------------- Delete a Book ----------------
        restTemplate.delete(serverUrl + "/{isbn}", "9780596009205");

        // ---------------- Get All After Deletion ----------------
        Books books = restTemplate.getForObject(serverUrl, Books.class);
        System.out.println(books.getBooks());
    }

	@Bean
	RestOperations restTemplate() {
		return new RestTemplate();
	}
}
