package com.ahmet.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Kullanici {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String sifre;

    @Transient
    private String sifreTekrar;

    private boolean aktif = false;

    private String aktivasyonKodu;

}