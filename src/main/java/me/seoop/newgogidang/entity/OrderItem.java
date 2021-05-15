package me.seoop.newgogidang.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oino;

    @ManyToOne
    @JoinColumn(name = "inum")
    private StoreItem storeItem;

    @ManyToOne
    @JoinColumn(name = "ono")
    private Order order;

    private int orderPrice;
    private int count;

    public void setStoreItem(StoreItem storeItem) {
        this.storeItem = storeItem;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void cancel() {
        storeItem.addStockQuantity(count);
    }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

    //==생성 메서드==//
    public static OrderItem createOrderItem(StoreItem storeItem, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setStoreItem(storeItem);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        storeItem.removeStockQuantity(count);
        return orderItem;
    }

}
