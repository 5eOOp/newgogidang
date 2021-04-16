package me.seoop.newgogidang.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = "store")
public class StoreImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgnum;

    private String uuid;
    private String imgName;
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;
}
