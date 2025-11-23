package app;

import java.util.List;

import domain.Address;
import domain.CreditCard;
import domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import repositories.CustomerRepository;
import domain.Customer;
import repositories.StudentRepository;

@SpringBootApplication
@EnableJpaRepositories("repositories")
@EntityScan("domain") 
public class CustomerApplication implements CommandLineRunner{
	
	@Autowired
	CustomerRepository customerrepository;

    @Autowired
    StudentRepository studentrepository;

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// create customer
		Customer customer = new Customer(101,"John doe", "johnd@acme.com", "0622341678");
		CreditCard creditCard = new CreditCard("12324564321", "Visa", "11/23");
		customer.setCreditCard(creditCard);
		customerrepository.save(customer);
		customer = new Customer(109,"John Jones", "jones@acme.com", "0624321234");
		creditCard = new CreditCard("657483342", "Visa", "09/23");
		customer.setCreditCard(creditCard);
		customerrepository.save(customer);
		customer = new Customer(66,"James Johnson", "jj123@acme.com", "068633452");
		creditCard = new CreditCard("99876549876", "MasterCard", "01/24");
		customer.setCreditCard(creditCard);
		customerrepository.save(customer);

//get customers
		System.out.println(customerrepository.findById(66).get());
		System.out.println(customerrepository.findById(101).get());
		System.out.println("-----------All customers ----------------");
		System.out.println(customerrepository.findAll());
		//update customer
		customer = customerrepository.findById(101).get();
		customer.setEmail("jd@gmail.com");
		customerrepository.save(customer);
		System.out.println("-----------find by phone ----------------");
		System.out.println(customerrepository.findByPhone("0622341678"));
		System.out.println("-----------find by email ----------------");
		System.out.println(customerrepository.findCustomerWithEmail("jj123@acme.com"));
		System.out.println("-----------find customers with a certain type of creditcard ----------------");
		List<Customer> customers = customerrepository.findCustomerWithCreditCardType("Visa");
		for (Customer cust : customers){
			System.out.println(cust);
		}

		System.out.println("-----------find by name ----------------");
		System.out.println(customerrepository.findByName("John doe"));


        // --- Create students ---
        Student student = new Student("Alice", "123456", "alice@mail.com",
                new Address("123 Main St", "Chicago", "60601"));
        studentrepository.save(student);

        student = new Student("Bob", "234567", "bob@mail.com",
                new Address("456 Oak St", "Chicago", "60602"));
        studentrepository.save(student);

        student = new Student("Charlie", "345678", "charlie@mail.com",
                new Address("789 Pine St", "New York", "10001"));
        studentrepository.save(student);

        student = new Student("Alice", "456789", "alice2@mail.com",
                new Address("321 Maple St", "Los Angeles", "90001"));
        studentrepository.save(student);

        student = new Student("Eve", "567890", "eve@mail.com",
                new Address("654 Elm St", "Chicago", "60603"));
        studentrepository.save(student);

        // --- Get students ---
        System.out.println("Find by ID:");
        System.out.println(studentrepository.findById(1).get());
        System.out.println(studentrepository.findById(2).get());

        System.out.println("-----------All students----------------");
        System.out.println(studentrepository.findAll());

        // --- Update student ---
        student = studentrepository.findById(1).get();
        student.setEmail("alice.new@mail.com");
        studentrepository.save(student);

        System.out.println("-----------Find by phone ----------------");
        System.out.println(studentrepository.findByPhoneNumber("123456"));

        System.out.println("-----------Find by name ----------------");
        System.out.println(studentrepository.findByName("Alice"));

        System.out.println("-----------Find by city ----------------");
        List<Student> studentsInChicago = studentrepository.findByAddressCity("Chicago");
        for (Student s : studentsInChicago) {
            System.out.println(s);
        }
		
	}

}
