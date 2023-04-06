package com.ankitacodes.Ecom.dao;

import com.ankitacodes.Ecom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByTitleContaining(String title);
    List<Product> findByLive(boolean live);

}
