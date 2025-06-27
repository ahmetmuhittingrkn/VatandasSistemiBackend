package com.example.vatandas_sistemi.service;

import com.example.vatandas_sistemi.dto.TalepCreateDto;
import com.example.vatandas_sistemi.dto.TalepDto;
import com.example.vatandas_sistemi.entity.enums.DurumEnum;
import com.example.vatandas_sistemi.entity.enums.OncelikEnum;
import com.example.vatandas_sistemi.entity.enums.TipEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface TalepService {
    
    TalepDto save(TalepCreateDto createDto);
    
    TalepDto findById(Long id);
    
    TalepDto findByTakipNo(String takipNo);
    
    List<TalepDto> findByVatandasId(Long vatandasId);
    
    Page<TalepDto> findByVatandasId(Long vatandasId, Pageable pageable);
    
    List<TalepDto> findByKategoriId(Long kategoriId);
    
    List<TalepDto> findByDurum(DurumEnum durum);
    
    List<TalepDto> findByTip(TipEnum tip);
    
    Page<TalepDto> findAll(Pageable pageable);
    
    Page<TalepDto> search(String baslik, String aciklama, TipEnum tip, 
                         DurumEnum durum, Long kategoriId, Pageable pageable);
    
    List<TalepDto> findByTarihAraligi(LocalDateTime baslangic, LocalDateTime bitis);
    
    TalepDto updateDurum(Long id, DurumEnum yeniDurum, String aciklama);
    
    TalepDto updateOncelik(Long id, OncelikEnum yeniOncelik);
    
    void deleteById(Long id);
    
    // İstatistik methodları
    Map<DurumEnum, Long> getDurumIstatistikleri();
    
    Map<TipEnum, Long> getTipIstatistikleri();
    
    Map<String, Long> getAylikIstatistikler(int yil);
    
    // Takip numarası oluşturma
    String generateTakipNo();
} 