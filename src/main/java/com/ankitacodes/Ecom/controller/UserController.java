package com.ankitacodes.Ecom.controller;

import com.ankitacodes.Ecom.constants.AppConstants;
import com.ankitacodes.Ecom.payloads.ApiResponse;
import com.ankitacodes.Ecom.payloads.ImageResponse;
import com.ankitacodes.Ecom.payloads.PageResponse;
import com.ankitacodes.Ecom.payloads.UserDto;
import com.ankitacodes.Ecom.service.FileService;
import com.ankitacodes.Ecom.service.UserService;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    /**
     * @author AnkitaM
     * @apiNote This api is created to Add a new user to the application.After creation user's status will be
     * saved as "active" user.
     * @Since 1.0v
     * @param userDto
     * @return
    **/

    //Create User
    @PostMapping("/user")
    public ResponseEntity<UserDto>createUser(@Valid @RequestBody UserDto userDto){

        logger.info("Initiating service call for save user");
        UserDto dto = this.userService.createUser(userDto);
        logger.info("completed service call for save user");

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    /**
     * @author AnkitaM
     * @apiNote This api is created to Update existing users details in the application
     * @Since 1.0v
     * @param userDto, userId
     * @throws com.ankitacodes.Ecom.exception.ResourceNotFoundException
     * @return
     **/
    //Update User
    @PutMapping("/user/{userId}")
    public ResponseEntity<UserDto>updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Long userId){

        logger.info("Initiating service call for update user with userId:{}",userId);
        UserDto updatedDto = this.userService.updateUser(userDto, userId);
        logger.info("completed service call for update user");

        return ResponseEntity.ok(updatedDto);
    }

    /**
     * @author AnkitaM
     * @apiNote This api is created to delete existing users from the application. After deletion user's status will be
     * saved as "inactive" user.
     * @Since 1.0v
     * @param userId
     * @throws com.ankitacodes.Ecom.exception.ResourceNotFoundException
     * @return
     **/

    //Delete User
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<ApiResponse>deleteUser(@PathVariable Long userId){

        logger.info("Initiating service call for delete user with userId:{}",userId);
         this.userService.deleteUser(userId);
        logger.info("completed service call for delete user");
        return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.SUCCESS, true), HttpStatus.OK);
    }

    /**
     * @author AnkitaM
     * @apiNote This api is created to find the user by userId.
     * @Since 1.0v
     * @param userId
     * @throws  com.ankitacodes.Ecom.exception.ResourceNotFoundException
     * @return
     **/

    //Get user by ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> findUserById(@PathVariable Long userId){

        logger.info("Initiating service call for finding user with userId:{}",userId);
        UserDto singleUserById = this.userService.getSingleUserById(userId);
        logger.info("completed service call for find user by userId");

        return ResponseEntity.ok(singleUserById);

    }

    /**
     * @author AnkitaM
     * @apiNote This api is created to find the user by emailId of user.
     * @Since 1.0v
     * @param email
     * @throws  com.ankitacodes.Ecom.exception.ResourceNotFoundException
     * @return
     **/
    //Get user by Email
    @GetMapping("/user/email/{email}")
    public ResponseEntity <UserDto> findUserByEmail(@PathVariable String email){

        logger.info("Initiating service call for finding user with emailId:{}",email);
        UserDto userByEmail = this.userService.getUserByEmail(email);
        logger.info("completed service call for find user by emailId");

        return ResponseEntity.ok(userByEmail);
    }

    /**
     * @author AnkitaM
     * @apiNote This api is created to find all the users in a list & represent them in page form.
     * @Since 1.0v
     * @param pageNumber,pageSize,sortBy,sortDir
     * @return
     **/
    //Get All users
    @GetMapping("/user")
    public ResponseEntity <PageResponse> getAllUser(@RequestParam (name="pageNumber", defaultValue =AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
                                                    @RequestParam(name="pageSize", defaultValue =AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                    @RequestParam(name="sortBy",defaultValue =AppConstants.SORT_BY_UID,required = false) String sortBy,
                                                    @RequestParam(name="sortDir",defaultValue = AppConstants.SORT_DIR) String sortDir ){

        logger.info("Initiating service call for finding all users");
        PageResponse allUsers = this.userService.getAllUsers(pageNumber,pageSize,sortBy,sortDir);
        logger.info("Completed service call for finding all users");

        return ResponseEntity.ok(allUsers);
    }

    /**
     * @author AnkitaM
     * @apiNote This api is created to find the user by entered keyword where keyword shall be name of user.
     * @Since 1.0v
     * @param keyword
     * @return
     **/
    //search User By name
    @GetMapping("/user/search/{keyword}")
    public ResponseEntity <UserDto> searchUser(@PathVariable("keyword") String keyword) {

        logger.info("Initiating dao call for searching user by keyword :{}",keyword);
        UserDto searchesdUserDto = this.userService.searchUser(keyword);
        logger.info("completed dao call for searching user by keyword");

        return ResponseEntity.ok(searchesdUserDto);
    }

    /**
     * @author AnkitaM
     * @apiNote This api is created to upload the user image for selected user
     * @Since 1.0v
     * @param image, userId
     * @return
     **/
    //upload User Image
    @PostMapping("user/image/{userId}")
    public ResponseEntity<ImageResponse>uploadUserImage(@RequestParam("userImage")MultipartFile image,
                                                        @PathVariable Long userId) throws IOException {

        logger.info("Initiating dao call for upload image for  user by userId :{}",userId);
        String imageName=fileService.uploadImage(image,imageUploadPath);

        UserDto user=userService.getSingleUserById(userId);
        user.setImageName(imageName);
        UserDto userDto = userService.updateUser(user, userId);

        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).message(AppConstants.USER_IMAGE_SUCCESS).build();

        logger.info("Completed dao call for upload image for  user by userId ");
        return new  ResponseEntity<ImageResponse>(imageResponse,HttpStatus.CREATED);

    }

    /**
     * @author AnkitaM
     * @apiNote This api is created to serve the user image as per selected user
     * @Since 1.0v
     * @param userId
     * @return
     **/
    //Serve User Image

    @GetMapping("/user/image/{userId}")
    public void serveUserImage(@PathVariable Long userId, HttpServletResponse response) throws IOException {

        logger.info("Initiating dao call for serve image for user as per userId :{}",userId);
        UserDto user = userService.getSingleUserById(userId);
        logger.info("User Image Name:{}",user.getImageName());
        InputStream resource = fileService.getResource(imageUploadPath,user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
        logger.info("Completed dao call  for serve image for user as per userId");
    }
}


