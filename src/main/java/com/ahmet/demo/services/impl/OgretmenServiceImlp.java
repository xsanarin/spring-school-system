package com.ahmet.demo.services.impl;

import com.ahmet.demo.dto.DersResponse;
import com.ahmet.demo.dto.OgrenciResponse;
import com.ahmet.demo.dto.OgretmenResponse;
import com.ahmet.demo.dto.OgretmenOgrenci;
import com.ahmet.demo.entities.Ders;
import com.ahmet.demo.entities.Ogrenci;
import com.ahmet.demo.entities.Ogretmen;
import com.ahmet.demo.repository.OgretmenRepository;
import com.ahmet.demo.services.IOgretmenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OgretmenServiceImlp implements IOgretmenService {

    @Autowired
    private OgretmenRepository ogretmenRepository;

    @Override
    public Ogretmen saveOgretmen(Ogretmen ogretmen) {
        return ogretmenRepository.save(ogretmen);
    }

    @Override
    public List<Ogretmen> getAllOgretmen() {
        return ogretmenRepository.findAll();
    }

    @Override
    public List<OgretmenResponse> getAllOgretmenNew() {
        List<OgretmenResponse> ogretmenResponseList = new ArrayList<>();
        List<Ogretmen> ogretmenList = ogretmenRepository.findAll();

        for (Ogretmen ogretmen : ogretmenList) {
            ogretmenResponseList.add(new OgretmenResponse(
                    ogretmen.getOgretmenAd(),
                    ogretmen.getOgretmenSoyad()
            ));
        }

        return ogretmenResponseList;
    }

    @Override
    public List<OgretmenOgrenci> ogretmen_ogrenci() {
        List<Ogretmen> ogretmenList = ogretmenRepository.findAll();
        List<OgretmenOgrenci> responseList = new ArrayList<>();

        for (Ogretmen ogretmen : ogretmenList) {
            Set<OgrenciResponse> ogrenciResponseSet = new HashSet<>();
            List<DersResponse> dersResponseList = new ArrayList<>();

            for (Ders ders : ogretmen.getDersIlikisi()) {
                DersResponse dersResponse = new DersResponse(
                        ders.getDersId(),
                        ders.getDersName(),
                        Collections.emptyList()
                );
                dersResponseList.add(dersResponse);

                for (Ogrenci ogrenci : ders.getOgrenciler()) {
                    ogrenciResponseSet.add(new OgrenciResponse(
                            ogrenci.getOgrenciId(),
                            ogrenci.getOgrenciAd(),
                            ogrenci.getOgrenciSoyad(),
                            ogrenci.getDurum(),
                            ogrenci.getVersion(),
                            Collections.emptyList()
                    ));
                }
            }

            OgretmenOgrenci ogretmenOgrenci = new OgretmenOgrenci(
                    ogretmen.getOgretmenAd(),
                    ogretmen.getOgretmenSoyad(),
                    new ArrayList<>(ogrenciResponseSet),
                    dersResponseList
            );

            responseList.add(ogretmenOgrenci);
        }

        return responseList;
    }

    @Override
    public Ogretmen getOgretmenById(int id) {
        return ogretmenRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteOgretmenById(int id) {
        Ogretmen dbOgretmen = getOgretmenById(id);
        if (dbOgretmen != null) {
            ogretmenRepository.delete(dbOgretmen);
        }
    }

    @Override
    public Ogretmen updateOgretmen(int id, Ogretmen updateOgretmen) {
        Ogretmen mevcut = ogretmenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Öğretmen bulunamadı: " + id));
        mevcut.setOgretmenAd(updateOgretmen.getOgretmenAd());
        mevcut.setOgretmenSoyad(updateOgretmen.getOgretmenSoyad());
        return ogretmenRepository.save(mevcut);
    }
}