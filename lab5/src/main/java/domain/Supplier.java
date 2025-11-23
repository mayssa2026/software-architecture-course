package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    @Id
    String supplierNumber;
    String name;
    String phone;
    String email;
    Address address;
}
