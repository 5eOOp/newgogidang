package me.seoop.newgogidang.entity;

import lombok.*;
import me.seoop.newgogidang.dto.MemberDTO;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"member"})
public class SellerRequest extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long srno;

    @OneToOne
    private Member member;

}
