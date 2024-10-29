package Online.Shopping.Platform.Controller;

import Online.Shopping.Platform.Entity.Furniture;
import Online.Shopping.Platform.Service.FurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/furniture")
public class FurnitureController {

    @Autowired
    private FurnitureService furnitureService;

    // Get all Furnitures
    @GetMapping
    public ResponseEntity<List<Furniture>> getAllFurnitures() {
        try{
            List<Furniture> furnitures=furnitureService.getAllFurniture();
            if(furnitures.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(furnitures);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    // Get a specific Furniture by ID
    @GetMapping("/{id}")
    public ResponseEntity<Furniture> getFurnitureById(@PathVariable String id) {
        Furniture Furniture=furnitureService.findFurnitureById(id);
        if(Furniture==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Furniture);
    }


    // Add a new Furniture
    @PostMapping
    public ResponseEntity<Furniture> addFurniture(@RequestBody Furniture furniture) {
        try {
            Furniture newFurniture=furnitureService.addFurniture(furniture);
            URI uri = URI.create("/api/Furnitures/" + newFurniture.getId());
            return ResponseEntity.created(uri).body(newFurniture);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    // Update the cost of a Furniture (for admin use)
    @PutMapping("/{id}/update-cost")
    public void updateFurnitureCost(@PathVariable String id, @RequestParam Double cost) {
        furnitureService.updateCost(id, cost);
    }

    // Purchase a Furniture (this involves making a payment through the Payment microservice)
    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseFurniture(@RequestParam Double amount, @RequestParam String currency) {
        return ResponseEntity.ok(furnitureService.purchaseFurniture(amount, currency).toString());
    }

    // Get payment details (test the connection with PaymentService)
    @GetMapping("/payment/{id}")
    public ResponseEntity<String> getPayment(@PathVariable String id) {
        return ResponseEntity.ok(furnitureService.getPayment(id).toString());
    }
}
