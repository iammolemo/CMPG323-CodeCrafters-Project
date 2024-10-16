package com.S2T.Share_2_Teach.service;

import com.S2T.Share_2_Teach.dto.Mailbody;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMessage(Mailbody mailbody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailbody.to());
        message.setFrom("jacoaucamp1470@gmail.com");
        message.setSubject(mailbody.subject());
        message.setText(mailbody.text());

        javaMailSender.send(message);
    }

}
