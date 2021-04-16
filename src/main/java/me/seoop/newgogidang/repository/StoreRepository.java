package me.seoop.newgogidang.repository;

import me.seoop.newgogidang.entity.Store;
import me.seoop.newgogidang.service.StoreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("select s, si, avg(coalesce(r.grade, 0)), count(distinct r) from Store s " +
            "left outer join StoreImg si on si.store = s " +
            "left outer join Review r on r.store = s group by s, si.imgnum")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select s, si, avg(coalesce(r.grade, 0)), count(r) " +
            "from Store s left outer join StoreImg si on si.store = s " +
            "left outer join Review r on r.store = s " +
            "where s.sno = :sno group by si.imgnum")
    List<Object[]> getStoreWithAll(@Param("sno") Long sno);
}
