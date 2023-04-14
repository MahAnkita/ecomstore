package com.ankitacodes.Ecom.service.serviceImpl;

import com.ankitacodes.Ecom.constants.AppConstants;
import com.ankitacodes.Ecom.exception.ResourceNotFoundException;
import com.ankitacodes.Ecom.dao.UserRepository;
import com.ankitacodes.Ecom.model.User;
import com.ankitacodes.Ecom.payloads.PageResponse;
import com.ankitacodes.Ecom.payloads.UserDto;
import com.ankitacodes.Ecom.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;
//    @Value("${user.profile.image.path}")
//    private String imagepath;

    @Autowired
    private ModelMapper modelmapper;
    @Override
    public UserDto createUser(UserDto userDto) {
//        User user=this.dtoToEntity(userDto);
//        User savedUser=this.userRepository.save(user);
//        UserDto savedDto=this.entityTodto(savedUser);
//        return savedDto;
        logger.info("Initiating dao call for save user");
       User user= this.modelmapper.map(userDto,User.class);
       user.setIsActive(AppConstants.ACTIVE);
       User savedUser= this.userRepository.save(user);

       UserDto saveddto= this.modelmapper.map(savedUser,UserDto.class);
        logger.info("completed dao call for save user");


       return saveddto;

    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {

        logger.info("Initiating dao call for update user with userId:{}",userId);
        User user =  this.userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException(AppConstants.USER_NOT_FOUND));
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setAbout(userDto.getAbout());
        user.setImageName(userDto.getImageName());

        User updatedUser = this.userRepository.save(user);
        UserDto updatedDto = this.modelmapper.map(user, UserDto.class);
        logger.info("completed dao call for update user");

        return updatedDto;
    }

    @Override
    public void deleteUser(Long userId) {

        logger.info("Initiating dao call for delete user with userId:{}",userId);
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND));

        this.userRepository.delete(user);
        //delete user's profile image
        //images/user/abc.png
//        String fullpath = imagepath + user.getImageName();
//        try{
//            Path path=Paths.get(fullpath);
//            Files.delete(path);
//        }catch(NoSuchFileException ex){
//            logger.info("User image not found in folder");
//            ex.printStackTrace();
//        }catch(IOException iox){
//            iox.printStackTrace();
//        }

        //user soft delete feature
//        user.setIsActive(AppConstants.INACTIVE);
//        this.userRepository.save(user);
//        logger.info("completed dao call for delete user");
    }

    @Override
    public UserDto getSingleUserById(Long userId) {

        logger.info("Initiating dao call for finding user with userId:{}",userId);
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND));
        UserDto dto = this.modelmapper.map(user, UserDto.class);
        logger.info("completed dao call for find user by userId");

        return dto;
    }

   @Override
    public UserDto getUserByEmail(String email) {

       logger.info("Initiating dao call for finding user with emailId:{}",email);
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND));
        UserDto userDto = this.modelmapper.map(user, UserDto.class);
       logger.info("completed dao call for find user by emailId");

        return userDto;
    }

    @Override
    public PageResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        logger.info("Initiating dao call for finding all users");

        //Logic to sort the user list as per user input in ascending or descending order
        Sort sort = (sortDir.equalsIgnoreCase("asc"))?(Sort.by(sortBy)).ascending():(Sort.by(sortBy)).descending();
       //logic to show user records pagewise as per user request
        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> userpages= this.userRepository.findAll(p);
        List<User> usersList= userpages.getContent();

       List<UserDto> dtoList = usersList.stream().map(user -> this.modelmapper.map(user, UserDto.class))
                .collect(Collectors.toList());

       PageResponse pgResponse = new PageResponse();
        pgResponse.setContent(dtoList);
        pgResponse.setPageNumber(userpages.getNumber());
        pgResponse.setPageSize(userpages.getSize());
        pgResponse.setTotalElements(userpages.getTotalElements());
        pgResponse.setTotalPages(userpages.getTotalPages());
        pgResponse.setIsLastPage(userpages.isLast());

       logger.info("Completed dao call for finding all users");

        return pgResponse;
    }

    @Override
    public UserDto searchUser(String keyword) {
        User user = this.userRepository.searchUserByName("%" + keyword + "%")
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND));
        UserDto dto = this.modelmapper.map(user, UserDto.class);
        return dto;
    }

//    private UserDto entityTodto(User savedUser){
//        UserDto dto = UserDto.builder()
//                .userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .password(savedUser.getPassword())
//                .about(savedUser.getAbout())
//                .gender(savedUser.getGender())
//                .imageName(savedUser.getImageName())
//                .build();
//        return dto;
//
//    }
//
//    private User dtoToEntity(UserDto savedUserDto){
//        User user = User.builder()
//                .userId(savedUserDto.getUserId())
//                .name(savedUserDto.getName())
//                .email(savedUserDto.getEmail())
//                .password(savedUserDto.getPassword())
//                .gender(savedUserDto.getGender())
//                .about(savedUserDto.getAbout())
//                .imageName(savedUserDto.getImageName())
//                .build();
//
//        return user;
//
//    }
}
