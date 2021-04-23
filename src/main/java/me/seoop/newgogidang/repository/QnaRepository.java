package me.seoop.newgogidang.repository;

import me.seoop.newgogidang.entity.Qna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QnaRepository extends JpaRepository<Qna, Long> {

    @Query("select q from Qna q")
    Page<Object[]> getListPage(Pageable pageable);
}
