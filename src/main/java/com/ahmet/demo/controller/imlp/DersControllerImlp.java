package com.ahmet.demo.controller.imlp;
import com.ahmet.demo.dto.DersResponse;
import com.ahmet.demo.entities.Ders;
import com.ahmet.demo.repository.DersRepository;
import com.ahmet.demo.services.AuthService;
import com.ahmet.demo.services.IDersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/rest/api/ders")
public class DersControllerImlp {

    @Autowired
    private IDersService dersService;

    @Autowired
    private AuthService authService;

    @GetMapping(path = "/list")
    public ResponseEntity<?> getAllDers(@RequestHeader("Authorization") String authHeader ) {
        ResponseEntity<String> authCheck = authService.checkAuthorization(authHeader);
        if (authCheck != null) return authCheck;
        List<Ders> dersList = dersService.getAllDers();
        return ResponseEntity.ok(dersList);
    }

    @GetMapping(path = "/ogretmen")
    public ResponseEntity<?> getNewDers(@RequestHeader("Authorization") String authHeader ) {
        ResponseEntity<String> authCheck = authService.checkAuthorization(authHeader);
        if (authCheck != null) return authCheck;
        List<DersResponse> ders=dersService.getNewDers();
        return  ResponseEntity.ok(ders);
    }

    @PostMapping(path = "/save")
    public ResponseEntity<?> saveDers(@RequestHeader("Authorization") String authHeader, @RequestBody Ders ders) {
        ResponseEntity<String> authCheck = authService.checkAuthorization(authHeader);
        if (authCheck != null) return authCheck;

        Ders savedDers = dersService.addDers(ders);
        return ResponseEntity.ok(savedDers);
    }
}
