package com.ankitacodes.Ecom.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="product_info")
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long productId;
    @Column(name="Title")
    private String title;
    @Column(name="Description",length = 1000)
    private String description;
    @Column(name="Price")
    private double price;
    @Column(name="Discounted_Price")
    private double discountedPrice;
    @Column(name="Quantity")
    private Integer quantity;
    @Column(name="Added_Date")
    private Date addedDate;
    @Column(name="live")
    private boolean live;
    @Column(name="stock")
    private boolean stock;
}
