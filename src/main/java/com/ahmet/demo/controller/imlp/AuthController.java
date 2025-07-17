package com.ahmet.demo.controller.imlp;

import com.ahmet.demo.JwtUtil;
import com.ahmet.demo.dto.AuthRequest;
import com.ahmet.demo.entities.Kullanici;
import com.ahmet.demo.services.AuthService;
import com.ahmet.demo.services.KullaniciService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tensorflow.TensorFlow;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private KullaniciService kullaniciService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest authRequest) {

        if (authRequest == null) {
            return ResponseEntity.badRequest().body("Lütfen Tüm parametreleri gönderin");
        }

        if (authRequest.getEmail() == null || authRequest.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Lütfen Email parametresini gönderin");
        }
        if ((authRequest.getSifre() == null || authRequest.getSifre().isEmpty())|| (authRequest.getSifreRepeat() == null
                || authRequest.getSifreRepeat().isEmpty())) {
            return ResponseEntity.badRequest().body("Lütfen Sifre parametresini gönderin");
        }

        if (!authRequest.getSifre().equals(authRequest.getSifreRepeat())) {
            return ResponseEntity.badRequest().body("Lütfen Sifreler birbiri ile uyuşmuyor parametresini gönderin");
        }

        kullaniciService.register(authRequest);
        return ResponseEntity.ok("Kayıt başarılı, lütfen mailinizi kontrol edin.");
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activate(@RequestParam String code) {
        boolean activated = kullaniciService.activateUser(code);
        if (activated) {
            return ResponseEntity.ok("Hesap aktifleştirildi");
        } else {
            return ResponseEntity.badRequest().body("Geçersiz aktivasyon kodu");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {

        Kullanici kullanici = kullaniciService.login(authRequest);
        String token = jwtUtil.generateToken(kullanici.getEmail());
        return ResponseEntity.ok(token);
    }
}