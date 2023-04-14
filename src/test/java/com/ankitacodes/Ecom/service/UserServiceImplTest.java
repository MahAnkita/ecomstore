package com.ankitacodes.Ecom.service;

import com.ankitacodes.Ecom.constants.AppConstants;
import com.ankitacodes.Ecom.dao.UserRepository;
import com.ankitacodes.Ecom.exception.ResourceNotFoundException;
import com.ankitacodes.Ecom.model.User;
import com.ankitacodes.Ecom.payloads.PageResponse;
import com.ankitacodes.Ecom.payloads.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceImplTest {


    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    //@InjectMocks
    @Autowired
    private UserService userService;

    User user;
    User user1;
    UserDto userDto;
    List<User> userList;

    Integer pn=1;
    Integer ps=10;
    String sortBy="userId";
    String dir="asc";

    @BeforeEach
    public void init(){
        user=User.builder().name("Ankita")
                .email("ankita@gmail.com")
                .userId(1L).password("Ankita123")
                .gender("Female").about("Test data")
                .imageName("ankita.png").build();

        user1=User.builder().name("Anurag")
                .email("anu@gmail.com")
                .userId(2L).password("Anu12345")
                .gender("Male").about("Test data")
                .imageName("anu.png").build();

        userDto=UserDto.builder().name("Ankita")
                .email("ankita@gmail.com")
                .userId(1L).password("Ankita123")
                .gender("Female").about("Test data")
                .imageName("ankita.png").build();

        userList=new ArrayList<>();
        userList.add(user);
        userList.add(user1);

    }
    @Test
    public void createUserTest(){

        //Arrange
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        //act
        UserDto user1 = userService.createUser(modelMapper.map(user, UserDto.class));
        System.out.println(user1.getName());
       //assret
        Assertions.assertNotNull(user1);
       // Assertions.assertEquals(user,user1);
        Assertions.assertEquals(user.getGender(),user1.getGender());
    }

    @Test
    public void updateUserTest(){

        Long userId=1L;
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto actualUser = userService.updateUser(userDto,userId);
        System.out.println(actualUser.getName());

        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(actualUser.getName(), user.getName());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(userDto, 111L));
    }

    @Test
    public void deleteUserTest(){

        Long userId=1L;
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        userService.deleteUser(userId);
        Mockito.verify(userRepository,Mockito.times(1)).delete(user);
       // Assertions.assertEquals();
        Assertions.assertThrows(ResourceNotFoundException.class,()->userService.deleteUser(111L));

    }
    @Test
    public void getAllUserTest() {
        Page<User> page = new PageImpl<>(userList);
        //Arrange
        Mockito.when(userRepository.findAll((Pageable)Mockito.any())).thenReturn(page);
        //Act
        PageResponse allUser=userService.getAllUsers(pn,ps,sortBy,dir);
        //Assert
        Assertions.assertEquals(2, allUser.getContent().size());
    }

    @Test
    public void getSingleUserByIdTest(){

        Long userId=1L;
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        UserDto actualUser=userService.getSingleUserById(userId);
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(actualUser.getUserId(),user.getUserId());
        Assertions.assertThrows(ResourceNotFoundException.class,()->userService.getSingleUserById(112L));
    }

    @Test
    public void getUserByEmailTest(){
        String email="ankita@test.com";

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        UserDto actualUser = userService.getUserByEmail(email);

        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(actualUser.getName(),user.getName());
        Assertions.assertEquals(actualUser.getPassword(),user.getPassword());
        Assertions.assertThrows(ResourceNotFoundException.class,()->userService.getUserByEmail("ankit@gmail.com"));

    }

//    @Test
//    public void searchUserTest(){
//        String keyword = "Ankita";
//
//        Mockito.when(userRepository.searchUserByName(keyword)).thenReturn(Optional.of(user));
//        UserDto actualUser = userService.searchUser(keyword);
//        Assertions.assertNotNull(actualUser);
//        Assertions.assertEquals(actualUser.getName(),user.getName());
//        Assertions.assertEquals(actualUser.getPassword(),user.getPassword());
//        //Assertions.assertThrows(ResourceNotFoundException.class,()->userService.searchUser("wrongname"));
//    }
}
