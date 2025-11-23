package webshop.lab5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"webshop.lab5", "controller", "service"})
@EnableMongoRepositories(basePackages = "repository")
public class Lab5Application {
	public static void main(String[] args) {
		SpringApplication.run(Lab5Application.class, args);
	}

}
