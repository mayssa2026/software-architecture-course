package books.integration;

import books.model.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsSender {
    @Autowired
    JmsTemplate jmsTemplate;
    public void sendMessage(Book book)  {
        try {
            //convert book to JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            String bookString = objectMapper.writeValueAsString(book);
            System.out.println("Sending a JMS message:" + bookString);
            jmsTemplate.convertAndSend("bookQueue", bookString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
