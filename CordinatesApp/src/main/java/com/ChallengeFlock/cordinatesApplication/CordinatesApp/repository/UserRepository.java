package com.ChallengeFlock.cordinatesApplication.CordinatesApp.repository;

import com.ChallengeFlock.cordinatesApplication.CordinatesApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);
}
