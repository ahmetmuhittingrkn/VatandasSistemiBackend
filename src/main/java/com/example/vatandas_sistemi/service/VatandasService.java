package com.example.vatandas_sistemi.service;

import com.example.vatandas_sistemi.dto.VatandasCreateDto;
import com.example.vatandas_sistemi.dto.VatandasDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VatandasService {
    
    VatandasDto save(VatandasCreateDto createDto);
    
    VatandasDto update(Long id, VatandasCreateDto updateDto);
    
    VatandasDto findById(Long id);
    
    VatandasDto findByEmail(String email);
    
    VatandasDto findByTcKimlikNo(String tcKimlikNo);
    
    List<VatandasDto> findAll();
    
    Page<VatandasDto> findAll(Pageable pageable);
    
    void deleteById(Long id);
    
    boolean existsByEmail(String email);
    
    boolean existsByTcKimlikNo(String tcKimlikNo);
} 