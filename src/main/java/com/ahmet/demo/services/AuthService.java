package com.ahmet.demo.services;

import com.ahmet.demo.JwtUtil;
import com.ahmet.demo.entities.Kullanici;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private KullaniciService kullaniciService;

    public ResponseEntity<String> checkAuthorization(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token gerekli");
        }

        String token = authHeader.substring(7);
        String email;
        try {
            email = jwtUtil.extractUsername(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Geçersiz token");
        }

        Kullanici kullanici = kullaniciService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        if (!kullanici.isAktif()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Lütfen önce hesabınızı aktifleştirin");
        }

        return null;
    }
}