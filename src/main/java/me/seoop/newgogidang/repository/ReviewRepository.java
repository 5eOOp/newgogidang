package me.seoop.newgogidang.repository;

import me.seoop.newgogidang.entity.Member;
import me.seoop.newgogidang.entity.Review;
import me.seoop.newgogidang.entity.Store;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByStore(Store store);

    @Modifying
    @Query("delete from Review r where r.member = :member")
    void deleteByMember(@Param("member") Member member);
}
