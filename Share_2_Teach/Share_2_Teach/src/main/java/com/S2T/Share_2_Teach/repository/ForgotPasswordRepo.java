package com.S2T.Share_2_Teach.repository;

import com.S2T.Share_2_Teach.entity.AppUsers;
import com.S2T.Share_2_Teach.entity.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ForgotPasswordRepo extends JpaRepository<ForgotPassword, Integer> {

    @Query("select forgotPassword from ForgotPassword  forgotPassword where forgotPassword.otp = ?1 and forgotPassword.appUsers = ?2")
    Optional<ForgotPassword> findByOtpAndUser(Integer otp, AppUsers appUsers);
}
