package me.seoop.newgogidang.repository;

import me.seoop.newgogidang.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("select n from Notice n")
    Page<Object[]> getListPage(Pageable pageable);
}
