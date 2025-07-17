package com.ahmet.demo.services;

import com.ahmet.demo.dto.DersResponse;
import com.ahmet.demo.entities.Ders;
import java.util.List;

public interface IDersService {
    List<Ders> getAllDers();
    public Ders addDers(Ders ders);
    public List<DersResponse> getNewDers();
}