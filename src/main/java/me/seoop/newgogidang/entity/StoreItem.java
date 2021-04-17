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
    private String imgName;
    private String path;

    private String itemName;
    private String itemPrice;
    private String itemGrade;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    public void changeItemName(String itemName) {
        this.itemName = itemName;
    }

    public void changeItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void changeItemGrade(String itemGrade) {
        this.itemGrade = itemGrade;
    }
}
