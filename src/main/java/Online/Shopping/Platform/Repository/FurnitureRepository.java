package Online.Shopping.Platform.Repository;

import Online.Shopping.Platform.Entity.Furniture;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FurnitureRepository extends MongoRepository<Furniture, String> {}
