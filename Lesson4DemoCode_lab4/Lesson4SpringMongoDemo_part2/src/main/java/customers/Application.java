package customers;

import customers.domain.Address;
import customers.domain.CreditCard;
import customers.domain.Customer;
import customers.domain.Student;
import customers.repository.CustomerRepository;
import customers.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;


@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private CustomerRepository customerRepository;
    @Autowired
    private StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        // create customer
		Customer customer = new Customer(101,"John doe", "johnd@acme.com", "0622341678");
		CreditCard creditCard = new CreditCard("12324564321", "Visa", "11/23");
		customer.setCreditCard(creditCard);
		customerRepository.save(customer);
		customer = new Customer(109,"John Jones", "jones@acme.com", "0624321234");
		creditCard = new CreditCard("657483342", "Visa", "09/23");
		customer.setCreditCard(creditCard);
		customerRepository.save(customer);
		customer = new Customer(66,"James Johnson", "jj123@acme.com", "068633452");
		creditCard = new CreditCard("99876549876", "MasterCard", "01/24");
		customer.setCreditCard(creditCard);
		customerRepository.save(customer);
		//get customers
		System.out.println(customerRepository.findById(66).get());
		System.out.println(customerRepository.findById(101).get());
		System.out.println("-----------All customers ----------------");
		System.out.println(customerRepository.findAll());
		//update customer
		customer = customerRepository.findById(101).get();
		customer.setEmail("jd@gmail.com");
		customerRepository.save(customer);
		System.out.println("-----------find by phone ----------------");
		System.out.println(customerRepository.findByPhone("0622341678"));
		System.out.println("-----------find by email ----------------");
		System.out.println(customerRepository.findCustomerWithEmail("jj123@acme.com"));
		System.out.println("-----------find customers with a certain type of creditcard ----------------");
		List<Customer> customers = customerRepository.findCustomerWithCreditCardType("Visa");
		for (Customer cust : customers){
			System.out.println(cust);
		}

		System.out.println("-----------find by name ----------------");
		System.out.println(customerRepository.findByName("John doe"));


        // --- Create students ---
        Student student = new Student("Alice", "123456", "alice@mail.com",
                new Address("123 Main St", "Chicago", "60601"));
        studentRepository.save(student);

        student = new Student("Bob", "234567", "bob@mail.com",
                new Address("456 Oak St", "Chicago", "60602"));
        studentRepository.save(student);

        student = new Student("Charlie", "345678", "charlie@mail.com",
                new Address("789 Pine St", "New York", "10001"));
        studentRepository.save(student);

        student = new Student("Alice", "456789", "alice2@mail.com",
                new Address("321 Maple St", "Los Angeles", "90001"));
        studentRepository.save(student);

        student = new Student("Eve", "567890", "eve@mail.com",
                new Address("654 Elm St", "Chicago", "60603"));
        studentRepository.save(student);

        // --- Get students by ID ---
        System.out.println("Find by ID:");
        System.out.println(studentRepository.findById("1").orElse(null));
        System.out.println(studentRepository.findById("2").orElse(null));

        // --- Get all students ---
        System.out.println("-----------All students----------------");
        studentRepository.findAll().forEach(System.out::println);

        // --- Update student ---
        student = studentRepository.findById("1").orElse(null);
        if (student != null) {
            student.setEmail("alice.new@mail.com");
            studentRepository.save(student);
        }

        System.out.println("-----------Find by phone ----------------");
        System.out.println(studentRepository.findByPhoneNumber("123456"));

        System.out.println("-----------Find by name ----------------");
        studentRepository.findByName("Alice").forEach(System.out::println);

        System.out.println("-----------Find by city ----------------");
        List<Student> studentsInChicago = studentRepository.findByAddressCity("Chicago");
        studentsInChicago.forEach(System.out::println);
	}

}
