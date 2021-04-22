package me.seoop.newgogidang.repository;

import me.seoop.newgogidang.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
