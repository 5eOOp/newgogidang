package me.seoop.newgogidang.repository;

import me.seoop.newgogidang.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
