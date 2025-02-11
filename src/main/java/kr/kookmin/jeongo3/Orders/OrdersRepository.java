package kr.kookmin.jeongo3.Orders;

import kr.kookmin.jeongo3.Orders.Dto.OrdersMapping;
import kr.kookmin.jeongo3.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, String> {

    List<OrdersMapping> findByUserOrderByTimeDesc(User user);

}
