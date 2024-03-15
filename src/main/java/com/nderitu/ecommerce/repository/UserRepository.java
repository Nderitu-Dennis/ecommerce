package com.nderitu.ecommerce.repository;

import com.nderitu.ecommerce.entity.User;
import com.nderitu.ecommerce.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository //interacts with the DB performing CRUD Operations
public interface UserRepository extends JpaRepository <User, Long>{

    Optional<User> findFirstByEmail(String email);

    User findByRole(UserRole userRole);  //this method will return User

}
