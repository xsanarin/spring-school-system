package com.ahmet.demo.controller.imlp;
import com.ahmet.demo.JwtUtil;
import com.ahmet.demo.dto.OgretmenResponse;
import com.ahmet.demo.dto.OgretmenOgrenci;
import com.ahmet.demo.entities.Kullanici;
import com.ahmet.demo.entities.Ogrenci;
import com.ahmet.demo.entities.Ogretmen;
import com.ahmet.demo.services.AuthService;
import com.ahmet.demo.services.IOgretmenService;
import com.ahmet.demo.services.KullaniciService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/api/ogretmen")
public class OgretmenControllerImlp {

    @PostMapping(path = "/save")
    public ResponseEntity<?> saveOgretmen(@RequestHeader("Authorization") String authHeader, @RequestBody Ogretmen ogretmen) {
        ResponseEntity<String> authCheck = authService.checkAuthorization(authHeader);
        if (authCheck != null) return authCheck;

        Ogretmen savedOgretmen = ogretmenService.saveOgretmen(ogretmen);
        return ResponseEntity.ok(savedOgretmen);
    }

    @GetMapping(path = "/list")
    public List<OgretmenResponse> getAllOgretmenNew() {
        return ogretmenService.getAllOgretmenNew();
    }

    @Autowired
    private IOgretmenService ogretmenService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private KullaniciService kullaniciService;

    @Autowired
    private AuthService authService;

    @GetMapping(path = "/ders")
    public ResponseEntity<?> getAllOgretmen() {
        List<Ogretmen> ogretmen = ogretmenService.getAllOgretmen();
        return ResponseEntity.ok(ogretmen);
    }


    @GetMapping("/ogrenciler")
    public List<OgretmenOgrenci> getOgretmenOgrenciListesi() {
        return ogretmenService.ogretmen_ogrenci();
    }

    @GetMapping(path = "/list/{id}")
    public Ogretmen getOgretmenById(@PathVariable(name = "id") int id) {
        return ogretmenService.getOgretmenById(id);
    }

    @DeleteMapping(path ="/delete/{id}")
    public void deleteOgretmenById(@PathVariable(name = "id") int id) {
        ogretmenService.deleteOgretmenById(id);
    }

    @PutMapping(path = "/update/{id}")
    public Ogretmen updateOgretmen(@PathVariable Integer id, @RequestBody Ogretmen updateOgretmen){
        return ogretmenService.updateOgretmen(id, updateOgretmen);
    }
}
