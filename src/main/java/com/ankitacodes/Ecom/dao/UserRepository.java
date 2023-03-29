package com.ankitacodes.Ecom.dao;

import com.ankitacodes.Ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {


    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.name like :key")
    Optional<User>searchUserByName(@Param("key")String name);


}
