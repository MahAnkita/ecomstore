package com.ankitacodes.Ecom.controller;

import com.ankitacodes.Ecom.constants.AppConstants;
import com.ankitacodes.Ecom.payloads.ApiResponse;
import com.ankitacodes.Ecom.payloads.ProductDto;
import com.ankitacodes.Ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto)
    {
        ProductDto savedProduct = this.productService.create(productDto);
        return new ResponseEntity<ProductDto>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto,@PathVariable Long productId)
    {
        ProductDto updatedProduct = this.productService.update(productDto, productId);
        return new ResponseEntity<ProductDto>(updatedProduct,HttpStatus.OK);
    }
    @GetMapping("/getById/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable Long productId)
    {
        ProductDto productDto = this.productService.getProductById(productId);
        return new ResponseEntity<ProductDto>(productDto,HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<ProductDto>> getAllProduct()
    {
        List<ProductDto> productDtos = this.productService.getAll();
        return new ResponseEntity<List<ProductDto>>(productDtos,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId)
    {
        this.productService.delete(productId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.SUCCESS,true),HttpStatus.OK);
    }

    @GetMapping("/searchTitle/{title}")
    public ResponseEntity<List<ProductDto>> searchByTitle(@PathVariable String title)
    {
        List<ProductDto> searchByTitle = this.productService.searchByTitle(title);
        return new ResponseEntity<List<ProductDto>>(searchByTitle,HttpStatus.OK);
    }

    @GetMapping("/search/{live}")
    public ResponseEntity<List<ProductDto>> searchBylive(@PathVariable boolean live)
    {
        List<ProductDto> searchByLive = this.productService.searchByLive(live);
        return new ResponseEntity<List<ProductDto>>(searchByLive,HttpStatus.OK);
    }
}
