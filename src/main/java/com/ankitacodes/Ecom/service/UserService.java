package com.ankitacodes.Ecom.service;

import com.ankitacodes.Ecom.payloads.PageResponse;
import com.ankitacodes.Ecom.payloads.UserDto;

import java.util.List;

public interface UserService {
    //create user
    UserDto createUser(UserDto userDto);

    //update user
    UserDto updateUser(UserDto userDto, Long userId);

    //delete user
    void deleteUser(Long userId);

    //get User by ID
    UserDto getSingleUserById(Long userId);

    //get User by email
    UserDto getUserByEmail(String email);

    //get all users
    PageResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir );

    //Search User
    UserDto searchUser(String keyword);

}
