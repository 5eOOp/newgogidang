package me.seoop.newgogidang.repository;

import me.seoop.newgogidang.entity.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreItemRepository extends JpaRepository<StoreItem, Long> {
}
