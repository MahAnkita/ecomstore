package com.ankitacodes.Ecom.service.serviceImpl;

import com.ankitacodes.Ecom.constants.AppConstants;
import com.ankitacodes.Ecom.dao.CategoryRepo;

import com.ankitacodes.Ecom.exception.ResourceNotFoundException;
import com.ankitacodes.Ecom.model.Category;

import com.ankitacodes.Ecom.payloads.CategoryDto;
import com.ankitacodes.Ecom.payloads.PageResponse;
import com.ankitacodes.Ecom.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServicempl implements CategoryService {
    Logger logger = LoggerFactory.getLogger(CategoryServicempl.class);
    @Autowired
    private CategoryRepo categoryRepo;

    @Value("${category.cover.image.path}")
    private String imagepath;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        logger.info("Initiating dao call for save category");
        Category cat = this.modelMapper.map(categoryDto, Category.class);
        Category savedCat = this.categoryRepo.save(cat);
        CategoryDto savedDto = this.modelMapper.map(savedCat,CategoryDto.class);
        logger.info("completed dao call for save category");

        return savedDto;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long catId) {
        logger.info("Initiating dao call for update category with catId:{}",catId);
        Category cat=this.categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND));
        cat.setTitle(categoryDto.getTitle());
        cat.setDescription(categoryDto.getDescription());
        cat.setCover_image(categoryDto.getCover_image());
        Category updatedCat=this.categoryRepo.save(cat);
        CategoryDto updatedDto=this.modelMapper.map(updatedCat,CategoryDto.class);

        logger.info("completed dao call for update category");
        return updatedDto;
    }

    @Override
    public void deleteCategory(Long catId) {
        logger.info("Initiating dao call for delete category with catId:{}",catId);
        Category cat = this.categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND));

        //delete user's profile image
        //images/category/abc.png
        String fullpath = imagepath + cat.getCover_image();
        try{
            Path path= Paths.get(fullpath);
            Files.delete(path);
        }catch(NoSuchFileException ex){
            logger.info("Category image not found in folder");
            ex.printStackTrace();
        }catch(IOException iox){
            iox.printStackTrace();
        }

        this.categoryRepo.delete(cat);
        logger.info("completed dao call for delete category");
    }

    @Override
    public PageResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        logger.info("Initiating dao call for finding all Categories");

        Sort sort = (sortDir.equalsIgnoreCase("asc"))?(Sort.by(sortBy)).ascending():(Sort.by(sortBy)).descending();
        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category>catPages= this.categoryRepo.findAll(p);
        List<Category> catList=catPages.getContent();

        List<CategoryDto>listCatDto =catList.stream()
                .map(cat->this.modelMapper.map(cat,CategoryDto.class)).collect(Collectors.toList());

        PageResponse pg = new PageResponse();
        pg.setCatcontent(listCatDto);
        pg.setPageNumber(catPages.getNumber());
        pg.setPageSize(catPages.getSize());
        pg.setTotalPages(catPages.getTotalPages());
        pg.setTotalElements(catPages.getTotalElements());
        pg.setIsLastPage(catPages.isLast());

        logger.info("completed dao call for finding all Categories");
        return pg;
    }

    @Override
    public CategoryDto getCategoryById(Long catId) {
        logger.info("Initiating dao call for finding category with catId:{}",catId);
        Category cat =this.categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND));
        CategoryDto catById = this.modelMapper.map(cat,CategoryDto.class);
        logger.info("completed dao call for find category by catId");
        return catById;
    }
}
