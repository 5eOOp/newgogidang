package me.seoop.newgogidang.repository;

import me.seoop.newgogidang.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
