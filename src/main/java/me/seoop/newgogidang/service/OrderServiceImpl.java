package me.seoop.newgogidang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.entity.Member;
import me.seoop.newgogidang.entity.Order;
import me.seoop.newgogidang.entity.OrderItem;
import me.seoop.newgogidang.entity.OrderStatus;
import me.seoop.newgogidang.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public Long register(Member member, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.WAITING);

        orderRepository.save(order);
        return order.getOno();
    }

    @Override
    public void cancel(Long ono) {
        Optional<Order> result = orderRepository.findById(ono);
        if(result.isPresent()) {
            Order order = result.get();
            if (order.getStatus() == OrderStatus.ORDER) {
                throw new IllegalStateException("이미 주문 완료된 상품입니다. 판매자에게 문의하세요.");
            }
            order.setStatus(OrderStatus.CANCEL);
            for (OrderItem orderItem : order.getOrderItems()) {
                orderItem.cancel();
            }
        }
    }

}
