package com.ahmet.demo.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ogretmen")
public class Ogretmen {

    @Id
    private Integer ogretmenId;

    @Column(name = "ogretmen_ad")
    private String ogretmenAd;

    @Column(name = "ogretmen_soyad")
    private String ogretmenSoyad;

    @ManyToMany
    @JoinTable(
            name = "ogretmen_ders_iliskisi",
            joinColumns = @JoinColumn(name = "ogretmen_id"),
            inverseJoinColumns = @JoinColumn(name = "ders_id") // <-- Burası eksikti
    )
    private List<Ders> dersIlikisi;
}