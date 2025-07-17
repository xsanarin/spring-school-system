package com.ahmet.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendActivationEmail(String toEmail, String aktivasyonKodu) {
        System.out.println("Mail gönderiliyor -> Alıcı: " + toEmail + ", Aktivasyon Kodu: " + aktivasyonKodu);

        SimpleMailMessage mesaj = new SimpleMailMessage();
        mesaj.setTo(toEmail);
        mesaj.setSubject("Hesap Aktivasyon");
        mesaj.setText("Aktivasyon için linke tıklayın: http://localhost:8081/api/auth/activate?code=" + aktivasyonKodu);

        try {
            mailSender.send(mesaj);
            System.out.println("Mail gönderildi.");
        } catch (Exception e) {
            System.err.println("Mail gönderilemedi: " + e.getMessage());
            e.printStackTrace();
        }
    }}