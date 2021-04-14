package me.seoop.newgogidang.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = "store")
public class StoreItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum;

    private String uuid;
    private String itemName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;
}
