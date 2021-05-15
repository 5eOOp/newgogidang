package me.seoop.newgogidang.entity;

import lombok.*;
import me.seoop.newgogidang.exception.NotEnoughStockException;

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
    private int itemPrice;
    private String itemGrade;
    private int stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    public void changeItemName(String itemName) {
        this.itemName = itemName;
    }

    public void changeItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void changeItemGrade(String itemGrade) {
        this.itemGrade = itemGrade;
    }

    public void addStockQuantity(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStockQuantity(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
