package com.example.vatandas_sistemi.service;

import com.example.vatandas_sistemi.dto.KategoriDto;

import java.util.List;

public interface KategoriService {
    
    KategoriDto findById(Long id);
    
    KategoriDto findByAd(String ad);
    
    List<KategoriDto> findAllAktif();
    
    List<KategoriDto> findAll();
    
    KategoriDto save(KategoriDto kategoriDto);
    
    KategoriDto update(Long id, KategoriDto kategoriDto);
    
    void deleteById(Long id);
    
    boolean existsByAd(String ad);
} 