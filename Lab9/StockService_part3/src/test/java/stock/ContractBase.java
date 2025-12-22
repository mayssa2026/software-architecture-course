package stock;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;

public class ContractBase {

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.standaloneSetup(new StockController());
    }
}
