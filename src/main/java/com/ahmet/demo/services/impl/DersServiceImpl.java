package com.ahmet.demo.services.impl;

import com.ahmet.demo.dto.DersResponse;
import com.ahmet.demo.dto.OgretmenResponse;
import com.ahmet.demo.entities.Ders;
import com.ahmet.demo.repository.DersRepository;
import com.ahmet.demo.services.IDersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DersServiceImpl implements IDersService {

    @Autowired
    private DersRepository dersRepository;

    @Override
    public List<Ders> getAllDers() {
        return dersRepository.findAll();
    }

    @Override
    public Ders addDers(Ders ders) {
        dersRepository.save(ders);
        return dersRepository.save(ders);
    }

    @Override
    public List<DersResponse> getNewDers() {
        List<DersResponse> dersResponseList = new ArrayList<>();
        List<Ders> dersList = dersRepository.findAll();

        if (dersList.isEmpty()){
            return null;
        }

        for (Ders ders : dersList) {
            List<OgretmenResponse> ogretmenDtos = new ArrayList<>();

            ders.getOgretmenler().forEach(ogretmen -> {
                ogretmenDtos.add(new OgretmenResponse(
                        ogretmen.getOgretmenAd(),
                        ogretmen.getOgretmenSoyad()
                ));
            });

            DersResponse response = new DersResponse(
                    ders.getDersId(),
                    ders.getDersName(),
                    ogretmenDtos
            );

            dersResponseList.add(response);
        }

        return dersResponseList;
    }}