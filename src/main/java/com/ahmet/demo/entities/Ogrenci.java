package com.ahmet.demo.entities;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="ogrenci")
public class Ogrenci {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ogrenci_id")
    private int ogrenciId;

    @Column(name = "ogrenci_ad")
    private String ogrenciAd;

    @Column(name = "ogrenci_soyad")
    private String ogrenciSoyad;

    @Column(name = "durum")
    private String durum;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "ogrenci_ders_iliski",
            joinColumns = @JoinColumn(name = "ogrenci_id"),
            inverseJoinColumns = @JoinColumn(name = "ders_id")
    )
    private Set<Ders> dersler;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;
}