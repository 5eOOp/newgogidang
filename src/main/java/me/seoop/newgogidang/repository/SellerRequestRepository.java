package me.seoop.newgogidang.repository;

import me.seoop.newgogidang.dto.MemberDTO;
import me.seoop.newgogidang.entity.SellerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SellerRequestRepository extends JpaRepository<SellerRequest, Long> {

}
