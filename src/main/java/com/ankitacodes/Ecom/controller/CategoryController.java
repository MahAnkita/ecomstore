package com.ankitacodes.Ecom.controller;

import com.ankitacodes.Ecom.constants.AppConstants;
import com.ankitacodes.Ecom.payloads.*;
import com.ankitacodes.Ecom.service.CategoryService;
import com.ankitacodes.Ecom.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/")
public class CategoryController {

    Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;
    @Value("${category.cover.image.path}")
    private String imageUploadPath;

    /**
     * @author AnkitaM
     * @apiNote This api is created to Add a new Category to the application.
     * @Since 1.0v
     * @param categoryDto
     * @return
     **/

    //Create category
    @PostMapping("/category")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){

        logger.info("Initiating service call for save category");
        CategoryDto dto = this.categoryService.addCategory(categoryDto);
        logger.info("completed service call for save category");

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    /**
     * @author AnkitaM
     * @apiNote This api is created to Update existing category details in the application
     * @Since 1.0v
     * @param categoryDto, catId
     * @throws com.ankitacodes.Ecom.exception.ResourceNotFoundException
     * @return
     **/
    //Update category
    @PutMapping("/category/{catId}")
    public ResponseEntity<CategoryDto>updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Long catId){

        logger.info("Initiating service call for update category with catId:{}",catId);
        CategoryDto updatedDto = this.categoryService.updateCategory(categoryDto,catId);
        logger.info("completed service call for update category");

        return ResponseEntity.ok(updatedDto);
    }

    /**
     * @author AnkitaM
     * @apiNote This api is created to delete existing category from the application.
     * @Since 1.0v
     * @param catId
     * @throws com.ankitacodes.Ecom.exception.ResourceNotFoundException
     * @return
     **/

    //Delete category
    @DeleteMapping("/category/{catId}")
    public ResponseEntity<ApiResponse>deleteCategory(@PathVariable Long catId){

        logger.info("Initiating service call for delete category with catId:{}",catId);
        this.categoryService.deleteCategory(catId);
        logger.info("completed service call for delete category");
        return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.SUCCESS, true), HttpStatus.OK);
    }

    /**
     * @author AnkitaM
     * @apiNote This api is created to find the category by catId.
     * @Since 1.0v
     * @param catId
     * @throws  com.ankitacodes.Ecom.exception.ResourceNotFoundException
     * @return
     **/

    //Get category by ID
    @GetMapping("/category/{catId}")
    public ResponseEntity<CategoryDto> findCategoryById(@PathVariable Long catId){

        logger.info("Initiating service call for finding category with catId:{}",catId);
        CategoryDto catById = this.categoryService.getCategoryById(catId);
        logger.info("completed service call for find category by catId");

        return ResponseEntity.ok(catById);

    }



    /**
     * @author AnkitaM
     * @apiNote This api is created to find all the categories in a list form & represent them in page form.
     * @Since 1.0v
     * @param pageNumber,pageSize,sortBy,sortDir
     * @return
     **/
    //Get All categories
    @GetMapping("/categories")
    public ResponseEntity <PageResponse> getAllCats(@RequestParam (name="pageNumber", defaultValue =AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
                                                    @RequestParam(name="pageSize", defaultValue =AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                    @RequestParam(name="sortBy",defaultValue =AppConstants.SORT_BY_CID,required = false) String sortBy,
                                                    @RequestParam(name="sortDir",defaultValue = AppConstants.SORT_DIR) String sortDir ){

        logger.info("Initiating service call for finding all categories");
        PageResponse allCats= this.categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortDir);
        logger.info("Completed service call for finding all categories");

        return ResponseEntity.ok(allCats);
    }

    /**
     * @author AnkitaM
     * @apiNote This api is created to upload the category's cover image for selected catID
     * @Since 1.0v
     * @param image, catId
     * @return
     **/
    //upload category cover Image
    @PostMapping("category/image/{catId}")
    public ResponseEntity<ImageResponse>uploadCoverImage(@RequestParam("catImage") MultipartFile image,
                                                        @PathVariable Long catId) throws IOException {

        logger.info("Initiating dao call for uploading the category's cover image for selected catID :{}",catId);
        String imageName=fileService.uploadImage(image,imageUploadPath);

        CategoryDto categoryById=this.categoryService.getCategoryById(catId);
        categoryById.setCover_image(imageName);
        CategoryDto categoryDto = categoryService.updateCategory(categoryById,catId);

        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).message(AppConstants.CATEGORY_IMAGE_SUCCESS).build();

        logger.info("Completed dao call for upload image for  user by userId ");
        return new  ResponseEntity<ImageResponse>(imageResponse,HttpStatus.CREATED);

    }

    /**
     * @author AnkitaM
     * @apiNote This api is created to serve the category image as per selected catId.
     * @Since 1.0v
     * @param catId
     * @return
     **/
    //Serve category cover Image

    @GetMapping("category/image/{catId}")
    public void serveUserImage(@PathVariable Long catId, HttpServletResponse response) throws IOException {

        logger.info("Initiating dao call for serve cover image for category as per catId :{}",catId);
        CategoryDto catDto = categoryService.getCategoryById(catId);

        logger.info("Category Image Name:{}",catDto.getCover_image());

        InputStream resource = fileService.getResource(imageUploadPath,catDto.getCover_image());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
        logger.info("Completed dao call  for serve cover image for category as per catId");
    }
}
