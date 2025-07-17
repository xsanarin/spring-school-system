package com.ahmet.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailTestRunner implements CommandLineRunner {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void run(String... args) throws Exception {
        SimpleMailMessage mesaj = new SimpleMailMessage();
        mesaj.setTo("seyyitahmet@gmail.com");
        mesaj.setSubject("Test Maili");
        mesaj.setText("Bu bir test mailidir.");
        mailSender.send(mesaj);
        System.out.println("Test maili gönderildi.");
    }
}