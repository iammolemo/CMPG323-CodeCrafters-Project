package com.S2T.Share_2_Teach.controller;

/*import com.S2T.Share_2_Teach.dto.Mailbody;
import com.S2T.Share_2_Teach.entity.AppUsers;
import com.S2T.Share_2_Teach.entity.ForgotPassword;
import com.S2T.Share_2_Teach.repository.ForgotPasswordRepo;
import com.S2T.Share_2_Teach.repository.UsersRepo;
import com.S2T.Share_2_Teach.service.ChangePassword;
import com.S2T.Share_2_Teach.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/forgotPassword")

public class ForgotPasswordController {

    private final UsersRepo usersRepo;

    private final EmailService emailService;

    private final ForgotPasswordRepo forgotPasswordRepo;
    private final PasswordEncoder passwordEncoder;

    public ForgotPasswordController(UsersRepo usersRepo, EmailService emailService, ForgotPasswordRepo forgotPasswordRepo, PasswordEncoder passwordEncoder) {
        this.usersRepo = usersRepo;
        this.emailService = emailService;
        this.forgotPasswordRepo = forgotPasswordRepo;
        this.passwordEncoder = passwordEncoder;
    }

    //Send mail for email verification
    @PostMapping("/verifyMail/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email) {
        AppUsers appUsers = usersRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email does not exist, please provide a valid one."));

        int otp = otpGenerator();
        Mailbody mailbody = Mailbody.builder()
                .to(email)
                .text("This is the OTP for your forgotten password request : "+otp)
                .subject("OTP For Forgotten Password")
                .build();
        ForgotPassword forgotPassword = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 70 * 1000))
                .appUsers(appUsers)
                .build();

        emailService.sendSimpleMessage(mailbody);
        forgotPasswordRepo.save(forgotPassword);

        return ResponseEntity.ok("Email has sent for verification.");

    }

    @PostMapping("/verifyOtp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email) {
        AppUsers appUsers = usersRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email does not exist, please provide a valid one."));

        ForgotPassword forgotPassword = forgotPasswordRepo.findByOtpAndUser(otp, appUsers)
                .orElseThrow(() -> new RuntimeException("Invalid OTP for email: " + email));


        if (forgotPassword.getExpirationTime().before(Date.from(Instant.now()))) {
            forgotPasswordRepo.deleteById(forgotPassword.getId());
            return new ResponseEntity<>("OTP has expired", HttpStatus.EXPECTATION_FAILED);
        }

        return ResponseEntity.ok("OTP verified.");

    }

    @PostMapping("/changePassword/{email}")
    public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePassword changePassword,
                                                        @PathVariable String email){
        if(!Objects.equals(changePassword.password(),changePassword.repeatPassword())) {
            return new ResponseEntity<>("Passwords do not match, please re-enter.", HttpStatus.EXPECTATION_FAILED);
        }

        String encodedPassword = passwordEncoder.encode(changePassword.password());
        usersRepo.updatePassword(email, encodedPassword);

        return ResponseEntity.ok("Password has been changed.");
    }

    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }
}
*/