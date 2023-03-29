package com.ankitacodes.Ecom.dao;

import com.ankitacodes.Ecom.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}
