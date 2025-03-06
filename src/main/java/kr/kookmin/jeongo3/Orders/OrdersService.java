package kr.kookmin.jeongo3.Orders;

import jakarta.transaction.Transactional;
import kr.kookmin.jeongo3.Exception.ErrorCode;
import kr.kookmin.jeongo3.Exception.MyException;
import kr.kookmin.jeongo3.Item.Item;
import kr.kookmin.jeongo3.Item.ItemRepository;
import kr.kookmin.jeongo3.Orders.Dto.RequestOrdersDto;
import kr.kookmin.jeongo3.Orders.Dto.OrdersMapping;
import kr.kookmin.jeongo3.User.User;
import kr.kookmin.jeongo3.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.kookmin.jeongo3.Exception.ErrorCode.ITEM_NOT_FOUND;
import static kr.kookmin.jeongo3.Exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final ItemRepository itemRepository;
    private final OrdersRepository ordersRepository;

    @Transactional
    public void saveOrders(RequestOrdersDto requestOrdersDto, User user) {
        Item item = itemRepository.findById(requestOrdersDto.getItemId()).orElseThrow(() -> new MyException(ITEM_NOT_FOUND));

        if (user.getPoint() - item.getPrice() < 0) {
            throw new MyException(ErrorCode.BAD_REQUEST);
        }
        user.setPoint(user.getPoint() - item.getPrice());

        Orders orders = new Orders(user, item);
        ordersRepository.save(orders);
    }

    public List<OrdersMapping> findAllOrders(User user) { // 페이징 처리 해야하나? 고민
        return ordersRepository.findByUserOrderByTimeDesc(user);
    }

}
