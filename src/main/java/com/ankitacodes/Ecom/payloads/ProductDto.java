package com.ankitacodes.Ecom.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long productId;
    @NotBlank(message="title is required!!")
    private String title;
    @NotBlank(message = "description is required!!")
    private String description;
    private double price;
    private double discountedPrice;
    private Integer quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;
}
