package com.ahmet.demo.repository;

import com.ahmet.demo.entities.Ogrenci;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  StudentRepository extends JpaRepository<Ogrenci,Integer>{
}
