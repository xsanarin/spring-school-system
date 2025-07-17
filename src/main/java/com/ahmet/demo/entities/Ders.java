package com.ahmet.demo.entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "ders")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Ders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ders_id")
    private int dersId;

    @Column(name = "ders_name")
    private String dersName;

    @ManyToMany(mappedBy = "dersler")
    @JsonIgnore
    private List<Ogrenci> ogrenciler;

    @ManyToMany(mappedBy = "dersIlikisi")
    @JsonIgnore
    private List<Ogretmen> ogretmenler;
}