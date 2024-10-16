package com.S2T.Share_2_Teach.repository;

import com.S2T.Share_2_Teach.entity.AppUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepo extends JpaRepository<AppUsers, Integer> {

    Optional<AppUsers> findByEmail(String email);
}
