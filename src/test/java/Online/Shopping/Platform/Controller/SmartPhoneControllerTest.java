package Online.Shopping.Platform.Controller;

import Online.Shopping.Platform.Entity.Payment;
import Online.Shopping.Platform.Entity.Furniture;
import Online.Shopping.Platform.Service.FurnitureService;
import Online.Shopping.Platform.Service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class SmartPhoneControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FurnitureService furnitureService;

    @InjectMocks
    private FurnitureController furnitureController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(furnitureController).build();
    }

    @Test
    void testGetAllFurnitures() throws Exception {
        Furniture furniture1 = new Furniture("1", "Vivi", 5,20000, "v5" );
        Furniture furniture2 = new Furniture("2", "Samsung", 2, 35000, "S7");
        List<Furniture> furnitures = Arrays.asList(furniture1, furniture2);

        when(furnitureService.getAllFurniture()).thenReturn(furnitures);

        ResponseEntity<List<Furniture>> response = furnitureController.getAllFurnitures();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(furnitures, response.getBody());
    }

    @Test
    void testGetFurnitureById() throws Exception {
        Furniture furniture = new Furniture("1", "Vivi", 5,20000, "v5" );
        when(furnitureService.findFurnitureById("1")).thenReturn(furniture);

        ResponseEntity<Furniture> response = furnitureController.getFurnitureById("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(furniture, response.getBody());
    }

    @Test
    void testGetFurnitureByIdNotFound() throws Exception {
        when(furnitureService.findFurnitureById("nonexistent")).thenReturn(null);

        ResponseEntity<Furniture> response = furnitureController.getFurnitureById("nonexistent");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddFurniture() throws Exception {
        Furniture furniture = new Furniture("1", "Vivi", 5,20000, "v5" );
        when(furnitureService.addFurniture(any(Furniture.class))).thenReturn(furniture);

        ResponseEntity<Furniture> response = furnitureController.addFurniture(furniture);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(furniture, response.getBody());
    }

    @Test
    void testUpdateFurnitureCost() throws Exception {
        doNothing().when(furnitureService).updateCost(anyString(), anyDouble());
        furnitureController.updateFurnitureCost("1", 79900.0);
    }

    @Test
    void testPurchaseFurniture() throws Exception {
        String purchaseResponse = "Purchase successful";
        Payment payment=new Payment(10000.0,"INR", "COMPLETE");
        when(furnitureService.purchaseFurniture(anyDouble(), anyString())).thenReturn(payment);
        ResponseEntity<String> response = furnitureController.purchaseFurniture(699.99, "USD");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
