package me.seoop.newgogidang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.entity.*;
import me.seoop.newgogidang.repository.MemberRepository;
import me.seoop.newgogidang.repository.OrderRepository;
import me.seoop.newgogidang.repository.StoreItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final StoreItemRepository storeItemRepository;

    @Override
    @Transactional
    public Long register(String email, Long inum, int count) {
        Member member = memberRepository.findByEmail(email);
        Optional<StoreItem> item = storeItemRepository.findById(inum);
        StoreItem storeItem = item.get();

        OrderItem orderItem = OrderItem.createOrderItem(storeItem, storeItem.getItemPrice(), count);

        Order order = Order.createOrder(member, orderItem);

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
