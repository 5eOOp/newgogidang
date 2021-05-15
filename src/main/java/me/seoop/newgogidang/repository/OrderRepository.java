package me.seoop.newgogidang.repository;

import me.seoop.newgogidang.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
