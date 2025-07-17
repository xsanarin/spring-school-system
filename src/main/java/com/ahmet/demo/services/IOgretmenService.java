package com.ahmet.demo.services;
import com.ahmet.demo.dto.OgretmenResponse;
import com.ahmet.demo.dto.OgretmenOgrenci;
import com.ahmet.demo.entities.Ogretmen;
import java.util.List;

public interface IOgretmenService {
    public Ogretmen saveOgretmen(Ogretmen ogretmen);
    public List<Ogretmen> getAllOgretmen();
    public List<OgretmenResponse> getAllOgretmenNew();

    List<OgretmenOgrenci> ogretmen_ogrenci();

    public  Ogretmen getOgretmenById(int id);
    void deleteOgretmenById(int id);
    public Ogretmen updateOgretmen(int id, Ogretmen updateOgretmen);

}
