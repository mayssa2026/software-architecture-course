package esb;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class ESBApplication implements CommandLineRunner {
	private final RestTemplate restTemplate;

	@Autowired
	public ESBApplication(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(ESBApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		restTemplate.postForLocation("http://localhost:8080/orders", new Order("334", 120.0));
		restTemplate.postForLocation("http://localhost:8080/orders", new Order("355", 185.0));
	}
}
