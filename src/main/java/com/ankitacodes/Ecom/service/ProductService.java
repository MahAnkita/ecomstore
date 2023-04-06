package com.ankitacodes.Ecom.service;

import com.ankitacodes.Ecom.payloads.ProductDto;

import java.util.List;

public interface ProductService {

    public ProductDto create(ProductDto productDto);
    public ProductDto update(ProductDto productDto,Long productId);
    public void delete(Long productId);
    public ProductDto getProductById(Long productId);
    public List<ProductDto> getAll();
    public List<ProductDto> searchByTitle(String title);
    public List<ProductDto> searchByLive(boolean live);
}
