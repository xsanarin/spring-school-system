package com.ahmet.demo.services;

import com.ahmet.demo.dto.AuthRequest;
import com.ahmet.demo.dto.RegisterRequest;
import com.ahmet.demo.entities.Kullanici;
import com.ahmet.demo.repository.KullaniciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class KullaniciService {

    @Autowired
    private KullaniciRepository kullaniciRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Optional<Kullanici> findByEmail(String email) {
        return kullaniciRepository.findByEmail(email);
    }

    @Transactional
    public Kullanici register(String email, String sifre) {
        if (email == null || email.isBlank()) {
            throw new RuntimeException("Email boş olamaz");
        }

        if (sifre == null || sifre.isBlank()) {
            throw new RuntimeException("Şifre boş olamaz");
        }

        if (kullaniciRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email zaten kayıtlı");
        }

        Kullanici kullanici = new Kullanici();
        kullanici.setEmail(email);
        kullanici.setSifre(passwordEncoder.encode(sifre));
        kullanici.setAktif(false);
        kullanici.setAktivasyonKodu(UUID.randomUUID().toString());

        kullaniciRepository.save(kullanici);

        try {
            mailService.sendActivationEmail(email, kullanici.getAktivasyonKodu());
        } catch (Exception e) {
            throw new RuntimeException("Mail gönderilemedi: " + e.getMessage());
        }

        return kullanici;
    }

    @Transactional
    public Kullanici register(AuthRequest request) {
        String email = request.getEmail();
        String sifre = request.getSifre();
        String sifreRepeat = request.getSifreRepeat();


        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email boş olamaz");
        }

        if (sifre == null || sifre.isBlank() || sifreRepeat == null || sifreRepeat.isBlank()) {
            throw new IllegalArgumentException("Şifre ve şifre tekrar boş olamaz");
        }

        if (!sifre.equals(sifreRepeat)) {
            throw new IllegalArgumentException("Şifreler eşleşmiyor");
        }

        Optional<Kullanici> mevcut = kullaniciRepository.findByEmail(email);

        if (mevcut.isPresent()) {
            Kullanici mevcutKullanici = mevcut.get();

            if (!mevcutKullanici.isAktif()) {
                mevcutKullanici.setAktivasyonKodu(UUID.randomUUID().toString());
                kullaniciRepository.save(mevcutKullanici);
                mailService.sendActivationEmail(mevcutKullanici.getEmail(), mevcutKullanici.getAktivasyonKodu());
                return mevcutKullanici;
            }

            throw new RuntimeException("Email zaten kayıtlı");
        }

        Kullanici kullanici = new Kullanici();
        kullanici.setEmail(email);
        kullanici.setSifre(passwordEncoder.encode(sifre));
        kullanici.setAktif(false);
        kullanici.setAktivasyonKodu(UUID.randomUUID().toString());

        kullaniciRepository.save(kullanici);
        mailService.sendActivationEmail(kullanici.getEmail(), kullanici.getAktivasyonKodu());

        return kullanici;
    }

    @Transactional
    public boolean activateUser(String kod) {
        Optional<Kullanici> optional = kullaniciRepository.findByAktivasyonKodu(kod);
        if (optional.isEmpty()) return false;

        Kullanici kullanici = optional.get();
        kullanici.setAktif(true);
        kullanici.setAktivasyonKodu(null);
        kullaniciRepository.save(kullanici);

        return true;
    }

    @Transactional
    public Kullanici login(AuthRequest authRequest) {
        Optional<Kullanici> optional = kullaniciRepository.findByEmail(authRequest.getEmail());
        if (optional.isEmpty()) {
            throw new RuntimeException("Kullanıcı bulunamadı");
        }

        Kullanici kullanici = optional.get();

        if (!kullanici.isAktif()) {
            throw new RuntimeException("Kullanıcı aktifleştirilmemiş");
        }

        if (!passwordEncoder.matches(authRequest.getSifre(), kullanici.getSifre())) {
            throw new RuntimeException("Şifre yanlış");
        }

        return kullanici;
    }
}