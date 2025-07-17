package com.ahmet.demo.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AuthRequest {
    private int authId;
    private String email;
    private String sifre;
    private String sifreRepeat;


}
