package me.seoop.newgogidang.repository;

import me.seoop.newgogidang.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select count(m) from Member m where m.email = :email and m.pw = :pw")
    int loginCheck(@Param("email") String email, @Param("pw") String pw);
}
