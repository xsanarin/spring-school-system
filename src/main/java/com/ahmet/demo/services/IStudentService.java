package com.ahmet.demo.services;
import com.ahmet.demo.dto.OgrenciResponse;
import com.ahmet.demo.entities.Ogrenci;
import java.util.List;

public interface IStudentService  {
     public Ogrenci saveOgrenci(Ogrenci ogrenci);
     public List<Ogrenci> getAllOgrenci();
     public List<OgrenciResponse> getAllOgrenciNew();
     public Ogrenci getOgrenciById(int id);
     public void deleteOgrenciById(int id);
     Ogrenci updateOgrenci(Integer id, Ogrenci updateOgrenci);
     Ogrenci addDersToOgrenci(int ogrenciId, int dersId);

}
