package com.ahmet.demo.repository;

import com.ahmet.demo.entities.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface KullaniciRepository extends JpaRepository<Kullanici, Long> {
    Optional<Kullanici> findByEmail(String email);
    Optional<Kullanici> findByAktivasyonKodu(String kod);
}