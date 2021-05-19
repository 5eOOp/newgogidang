package me.seoop.newgogidang.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "m_member")
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mid;

    private String email;
    private String pw;
    private String nickname;
    private boolean fromSocial;

    @OneToOne
    @JoinColumn(name = "store_sno")
    private Store store;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public void addMemberRole(MemberRole memberRole) {
        roleSet.add(memberRole);
    }

    public void changePw(String pw) {
        this.pw = pw;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
