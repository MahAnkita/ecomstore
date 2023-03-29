package com.ankitacodes.Ecom.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Category_info")
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long catId;
    @Column(name="Category_Title")
    private String title;
    @Column(name="Category_Description")
    private String description;
    @Column(name="Category_Cover_Image")
    private String cover_image;

}
