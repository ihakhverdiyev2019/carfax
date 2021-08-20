package az.elixir.vin.repository;

import az.elixir.vin.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity,Integer> {

    OrderEntity findById(int id);

    OrderEntity findByVinCode(String vinCode);

}
