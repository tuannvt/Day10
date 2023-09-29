package repository;

import entity.BookEntity;
import entity.OrdersEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends CrudRepository<OrdersEntity,Integer> {
//    @Query(value = "select month(orderDate)  from day04.orders where month(orderDate)=?1")
//    List<OrdersEntity> findByOrderDate(int month);

}
