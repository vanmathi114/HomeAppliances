package Online.Shopping.Platform.Service;

import Online.Shopping.Platform.Entity.Payment;
import Online.Shopping.Platform.Exception.ProductNotFound;
import Online.Shopping.Platform.Exception.DatabaseOperationException;
import Online.Shopping.Platform.Repository.FurnitureRepository;
import Online.Shopping.Platform.Entity.Furniture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private FurnitureRepository furnitureRepository;
    private final PaymentClient paymentClient;

    @Autowired
    public ProductService(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    public Payment purchaseFurniture(Double amount, String currency) {
        Payment payment = paymentClient.makePayment(amount, currency);
        return payment;
    }

    public Payment getPayment(String paymentId) {
        return paymentClient.getPayment(paymentId);
    }


    public Furniture addfurniture(Furniture furniture) {
        try {
            return furnitureRepository.save(furniture);
        } catch (Exception e) {
            throw new DatabaseOperationException("Unexpected error occurred while saving the furniture", e);
        }
    }

    public List<Furniture> getAllFurniture() {
        return furnitureRepository.findAll();
    }

    public Furniture findfurnitureById(String Id) {

        return furnitureRepository.findById(Id).
                orElseThrow(() -> new ProductNotFound("furniture with ID " + Id + " not found"));
    }

    public void updateCost(String id, double newCost) {
        Furniture furniture = furnitureRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        furniture.setCost(newCost);
        try {
            furnitureRepository.save(furniture);
        } catch (Exception e) {
            throw new DatabaseOperationException("Unexpected error occurred while saving the furniture", e);
        }
    }
}
