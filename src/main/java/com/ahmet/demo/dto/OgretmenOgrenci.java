package com.ahmet.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OgretmenOgrenci {
    private String ogretmenAd;
    private String ogretmenSoyad;
    private List<OgrenciResponse> ogrenciler;
    private List<DersResponse> dersler;
}