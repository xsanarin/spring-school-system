package com.ahmet.demo.services.impl;
import com.ahmet.demo.dto.DersResponse;
import com.ahmet.demo.dto.OgrenciResponse;
import com.ahmet.demo.dto.OgretmenResponse;
import com.ahmet.demo.entities.Ders;
import com.ahmet.demo.entities.Ogrenci;
import com.ahmet.demo.repository.DersRepository;
import com.ahmet.demo.repository.StudentRepository;
import com.ahmet.demo.services.IStudentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentServiceImlp implements IStudentService  {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    private DersRepository dersRepository;

    public Ogrenci saveOgrenci(Ogrenci ogrenci) {
        Set<Ders> dersSet = new HashSet<>();

        if (ogrenci.getDersler() != null) {
            for (Ders gelenDers : ogrenci.getDersler()) {
                dersRepository.findById(gelenDers.getDersId()).ifPresent(ders -> {
                    if (!dersSet.contains(ders)) {
                        dersSet.add(ders);
                    }
                });
            }
        }

        ogrenci.setDersler(dersSet);
        return studentRepository.save(ogrenci);
    }
    @Override
    public List<Ogrenci> getAllOgrenci()   {
        List <Ogrenci> studentList= studentRepository.findAll();
        return studentList;
    }

    @Override
    public List<OgrenciResponse> getAllOgrenciNew() {
        List<Ogrenci> studentList = studentRepository.findAll();
        List<OgrenciResponse> studentListResponse = new ArrayList<>();

        if (studentList.isEmpty()) {
            return studentListResponse;
        }

        studentList.forEach(student -> {
            List<DersResponse> dersResponseList = new ArrayList<>();

            if (student.getDersler() != null) {
                student.getDersler().forEach(ders -> {
                    List<OgretmenResponse> ogretmenResponseList = new ArrayList<>();

                    if (ders.getOgretmenler() != null) {
                        ders.getOgretmenler().forEach(ogretmen -> {
                            ogretmenResponseList.add(new OgretmenResponse(
                                    ogretmen.getOgretmenAd(),
                                    ogretmen.getOgretmenSoyad()
                            ));
                        });
                    }

                    dersResponseList.add(new DersResponse(
                            ders.getDersId(),
                            ders.getDersName(),
                            ogretmenResponseList
                    ));
                });
            }

            studentListResponse.add(new OgrenciResponse(
                    student.getOgrenciId(),
                    student.getOgrenciAd(),
                    student.getOgrenciSoyad(),
                    student.getDurum(),
                    student.getVersion(),
                    dersResponseList
            ));
        });

        return studentListResponse;
    }

    @Override
    public Ogrenci getOgrenciById(int id) {
        Optional<Ogrenci> optional=studentRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteOgrenciById(int id) {
        Ogrenci ogrenci = studentRepository.findById(id).orElse(null);
        if (ogrenci != null) {
            ogrenci.getDersler().clear();  // join tablosundaki ilişkileri temizle
            studentRepository.save(ogrenci); // değişiklikleri kaydet
            studentRepository.deleteById(id); // öğrenciyi sil
        }
    }

    @Override
    public Ogrenci updateOgrenci(Integer id, Ogrenci updateOgrenci) {
        Optional<Ogrenci> optionalOgrenci = studentRepository.findById(id);
        if (optionalOgrenci.isPresent()) {
            Ogrenci mevcutOgrenci = optionalOgrenci.get();
            mevcutOgrenci.setOgrenciAd(updateOgrenci.getOgrenciAd());
            mevcutOgrenci.setOgrenciSoyad(updateOgrenci.getOgrenciSoyad());
            mevcutOgrenci.setDurum(updateOgrenci.getDurum());

            return studentRepository.save(mevcutOgrenci);
        } else {
            throw new RuntimeException("Öğrenci bulunamadı: ID = " + id);
        }
    }

    @Override
    public Ogrenci addDersToOgrenci(int ogrenciId, int dersId) {
        Ogrenci ogrenci = studentRepository.findById(ogrenciId)
                .orElseThrow(() -> new RuntimeException("Öğrenci bulunamadı"));

        Ders ders = dersRepository.findById(dersId)
                .orElseThrow(() -> new RuntimeException("Ders bulunamadı"));

        Set<Ders> dersler = ogrenci.getDersler();

        if (dersler == null) {
            dersler = new HashSet<>();
            ogrenci.setDersler(dersler);
        }

        if (dersler.contains(ders)) {
            throw new RuntimeException("Öğrenci zaten bu derse kayıtlı");
        }

        if (dersler.size()>=3){
            throw new RuntimeException("Max 3 ders alınabilir");
        }

        dersler.add(ders);

        List<Ogrenci> ogrenciler = ders.getOgrenciler();

        if (ogrenciler == null) {
            ogrenciler = new ArrayList<>();
            ders.setOgrenciler(ogrenciler);
        }

        if (!ogrenciler.contains(ogrenci)) {
            ogrenciler.add(ogrenci);
        }
        return studentRepository.save(ogrenci);
    }
}

