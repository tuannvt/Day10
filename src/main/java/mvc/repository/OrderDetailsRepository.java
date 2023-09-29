package repository;

import entity.OrderDetailsEntity;
import entity.OrdersEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderDetailsRepository extends CrudRepository<OrderDetailsEntity,Integer> {
//    @Query(value = "select * from orderDetails\n" +
//            "where orderId=?1")
    List<OrderDetailsEntity> findByProductNameContaining(String productName);

}
