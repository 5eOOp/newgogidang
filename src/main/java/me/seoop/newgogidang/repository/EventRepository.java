package me.seoop.newgogidang.repository;

import me.seoop.newgogidang.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("select e from Event e")
    Page<Object[]> getListPage(Pageable pageable);
}
