package com.ahmet.demo.controller.imlp;

import com.ahmet.demo.dto.OgrenciResponse;
import com.ahmet.demo.entities.Ogrenci;
import com.ahmet.demo.services.AuthService;
import com.ahmet.demo.services.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/rest/api/ogrenci")
@CrossOrigin(origins = "*")
public class StudentControllerImlp {

    @Autowired
    private IStudentService studentService;

    @Autowired
    private AuthService authService;


    @PostMapping(path = "/save")
    public ResponseEntity<?> saveOgrenci(@RequestHeader("Authorization") String authHeader, @RequestBody Ogrenci ogrenci) {
        ResponseEntity<String> authCheck = authService.checkAuthorization(authHeader);
        if (authCheck != null) return authCheck;

        Ogrenci saved = studentService.saveOgrenci(ogrenci);
        return ResponseEntity.ok(saved);
    }

    @GetMapping(path = "/ders")
    public ResponseEntity<?> getAllOgrenci() {
        List<Ogrenci> ogrenciler = studentService.getAllOgrenci();
        return ResponseEntity.ok(ogrenciler);
    }

    @GetMapping(path = "/list")
    public ResponseEntity<?> getAllOgrenciNew() {
        List<OgrenciResponse> ogrenciler = studentService.getAllOgrenciNew();
        return ResponseEntity.ok(ogrenciler);
    }

    @GetMapping(path = "/list/{id}")
    public ResponseEntity<?> getOgrenciById(@PathVariable(name = "id") int id) {
        Ogrenci ogrenci = studentService.getOgrenciById(id);
        return ResponseEntity.ok(ogrenci);
    }

    @DeleteMapping(path ="/delete/{id}")
    public ResponseEntity<?> deleteOgrenciById(@RequestHeader("Authorization") String authHeader,
                                               @PathVariable(name = "id") int id) {
        ResponseEntity<String> authCheck = authService.checkAuthorization(authHeader);
        if (authCheck != null) return authCheck;

        studentService.deleteOgrenciById(id);
        return ResponseEntity.ok("Öğrenci silindi");
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> updateOgrenci(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id,
                                           @RequestBody Ogrenci updateOgrenci) {
        ResponseEntity<String> authCheck = authService.checkAuthorization(authHeader);
        if (authCheck != null) return authCheck;

        Ogrenci updated = studentService.updateOgrenci(id, updateOgrenci);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{ogrenciId}/ders/{dersId}")
    public ResponseEntity<?> addDersToOgrenci(@PathVariable int ogrenciId, @PathVariable int dersId) {
        try {
            Ogrenci ogrenci = studentService.addDersToOgrenci(ogrenciId, dersId);
            return ResponseEntity.ok(ogrenci);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}