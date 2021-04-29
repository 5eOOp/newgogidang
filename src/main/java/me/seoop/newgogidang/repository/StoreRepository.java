package me.seoop.newgogidang.repository;

import com.querydsl.core.BooleanBuilder;
import me.seoop.newgogidang.entity.Store;
import me.seoop.newgogidang.service.StoreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long>, QuerydslPredicateExecutor<Store> {

    @Query("select s, si, avg(coalesce(r.grade, 0)), count(distinct r) from Store s " +
            "left outer join StoreImg si on si.store = s " +
            "left outer join Review r on r.store = s group by s, si.imgnum")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select s, si, avg(coalesce(r.grade, 0)), count(distinct r) from Store s " +
            "left outer join StoreImg si on si.store = s " +
            "left outer join Review r on r.store = s group by s, si.imgnum order by avg(coalesce(r.grade, 0)) desc")
    Page<Object[]> getListPageWithAvgDesc(Pageable pageable);

    @Query("select s, si, avg(coalesce(r.grade, 0)), count(distinct r) from Store s " +
            "left outer join StoreImg si on si.store = s " +
            "left outer join Review r on r.store = s group by s, si.imgnum order by count(distinct r) desc")
    Page<Object[]> getListPageWithCountDesc(Pageable pageable);

    @Query("select s, si, avg(coalesce(r.grade, 0)), count(r), it " +
            "from Store s left outer join StoreImg si on si.store = s " +
            "left outer join Review r on r.store = s " +
            "left outer join StoreItem it on it.store = s " +
            "where s.sno = :sno group by si.imgnum, it.inum")
    List<Object[]> getStoreWithAll(@Param("sno") Long sno);
}
