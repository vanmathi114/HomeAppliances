package Online.Shopping.Platform.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Smartphone extends Product {
    public Smartphone(String Id, String name, int stock, double cost, String model) {
        super(Id, name, stock, cost, model);
    }
}
