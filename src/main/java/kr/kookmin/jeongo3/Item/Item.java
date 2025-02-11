package kr.kookmin.jeongo3.Item;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "ITEM_ID")
    private String id;

    @Column
    private String name;

    @Column
    private String image;

    @Column
    private int price;

    @Builder
    public Item(String name, String image, int price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }
}
