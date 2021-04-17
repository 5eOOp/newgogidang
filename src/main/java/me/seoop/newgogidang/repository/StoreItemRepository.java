package me.seoop.newgogidang.repository;

import me.seoop.newgogidang.entity.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreItemRepository extends JpaRepository<StoreItem, Long> {

    @Query("select count(it) from StoreItem it where it.store.sno = :sno")
    int countBySno(@Param("sno") Long sno);
}
