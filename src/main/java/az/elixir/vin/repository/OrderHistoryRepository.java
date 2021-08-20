package az.elixir.vin.repository;

import az.elixir.vin.entity.OrderHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHistoryRepository extends CrudRepository<OrderHistory ,Integer> {

    List<OrderHistory> findAllByCustomerId(int id);
    OrderHistory findByCustomerIdAndAndOrderId(int custId, int orderId);

}
