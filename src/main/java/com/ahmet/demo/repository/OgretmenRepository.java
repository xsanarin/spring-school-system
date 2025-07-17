package com.ahmet.demo.repository;

import com.ahmet.demo.entities.Ogretmen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OgretmenRepository extends JpaRepository<Ogretmen,Integer> {
}
