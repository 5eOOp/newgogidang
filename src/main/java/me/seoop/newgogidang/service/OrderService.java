package me.seoop.newgogidang.service;

import me.seoop.newgogidang.entity.Member;
import me.seoop.newgogidang.entity.Order;
import me.seoop.newgogidang.entity.OrderItem;

public interface OrderService {

    Long register(String email, Long inum, int count);
    void cancel(Long ono);
}
