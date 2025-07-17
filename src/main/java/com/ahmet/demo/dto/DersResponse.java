package com.ahmet.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DersResponse {
    private int dersId;
    private String dersName;
    private List<OgretmenResponse> ogretmenler;
}