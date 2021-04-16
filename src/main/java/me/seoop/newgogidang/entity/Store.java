package me.seoop.newgogidang.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Store extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sno;

    private String title;
    private String address;
    private String phone;

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

    public void changePhone(String phone) {
        this.phone = phone;
    }


}
