package com.example.vatandas_sistemi.repository;

import com.example.vatandas_sistemi.entity.Kategori;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KategoriRepository extends JpaRepository<Kategori, Long> {
    
    Optional<Kategori> findByAd(String ad);
    
    @Query("SELECT k FROM Kategori k WHERE k.aktif = true ORDER BY k.siraNo ASC, k.ad ASC")
    List<Kategori> findAllAktifKategoriler();
    
    @Query("SELECT k FROM Kategori k WHERE k.aktif = true AND k.id = :id")
    Optional<Kategori> findByIdAndAktif(Long id);
    
    boolean existsByAd(String ad);
} 