package Online.Shopping.Platform.Service;

import Online.Shopping.Platform.Entity.Payment;
import Online.Shopping.Platform.Entity.Furniture;
import Online.Shopping.Platform.Exception.DatabaseOperationException;
import Online.Shopping.Platform.Exception.ProductNotFound;
import Online.Shopping.Platform.Repository.FurnitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FurnitureService {

    @Autowired
    private FurnitureRepository furnitureRepository;
    private final PaymentClient paymentClient;

    @Autowired
    public FurnitureService(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    public Payment purchaseFurniture(Double amount, String currency) {
        Payment payment = paymentClient.makePayment(amount, currency);
        return payment;
    }

    public Payment getPayment(String paymentId) {
        return paymentClient.getPayment(paymentId);
    }


    public Furniture addFurniture(Furniture furniture) {
        try {
            return furnitureRepository.save(furniture);
        } catch (Exception e) {
            throw new DatabaseOperationException("Unexpected error occurred while saving the furniture", e);
        }
    }

    public List<Furniture> getAllFurniture() {
        return furnitureRepository.findAll();
    }

    public Furniture findFurnitureById(String Id) {

        return furnitureRepository.findById(Id).
                orElseThrow(() -> new ProductNotFound("Furniture with ID " + Id + " not found"));
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
