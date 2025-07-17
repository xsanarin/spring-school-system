package com.ahmet.demo.repository;

import com.ahmet.demo.entities.Ders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DersRepository extends JpaRepository<Ders,Integer> {
}
