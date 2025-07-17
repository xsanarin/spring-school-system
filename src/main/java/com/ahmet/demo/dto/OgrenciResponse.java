package com.ahmet.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OgrenciResponse {
    private int ogrenciId;
    private String ogrenciAd;
    private String ogrenciSoyad;
    private String durum;
    private Integer version;
    private List<DersResponse> dersler;
}