package com.example.vatandas_sistemi.service.impl;

import com.example.vatandas_sistemi.dto.KategoriDto;
import com.example.vatandas_sistemi.entity.Kategori;
import com.example.vatandas_sistemi.exception.BusinessException;
import com.example.vatandas_sistemi.exception.ResourceNotFoundException;
import com.example.vatandas_sistemi.mapper.KategoriMapper;
import com.example.vatandas_sistemi.repository.KategoriRepository;
import com.example.vatandas_sistemi.service.KategoriService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class KategoriServiceImpl implements KategoriService {
    
    private final KategoriRepository kategoriRepository;
    private final KategoriMapper kategoriMapper;
    
    @Override
    @Transactional(readOnly = true)
    public KategoriDto findById(Long id) {
        Kategori kategori = kategoriRepository.findByIdAndAktif(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kategori", id));
        return convertToDto(kategori);
    }
    
    @Override
    @Transactional(readOnly = true)
    public KategoriDto findByAd(String ad) {
        Kategori kategori = kategoriRepository.findByAd(ad)
                .orElseThrow(() -> new ResourceNotFoundException("Kategori bulunamadı: " + ad));
        return convertToDto(kategori);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<KategoriDto> findAllAktif() {
        return kategoriRepository.findAllAktifKategoriler().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<KategoriDto> findAll() {
        return kategoriRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public KategoriDto save(KategoriDto kategoriDto) {
        log.info("Yeni kategori oluşturuluyor: {}", kategoriDto.getAd());
        
        if (kategoriRepository.existsByAd(kategoriDto.getAd())) {
            throw new BusinessException("Bu kategori adı zaten kullanımda: " + kategoriDto.getAd());
        }
        
        Kategori kategori = Kategori.builder()
                .ad(kategoriDto.getAd())
                .aciklama(kategoriDto.getAciklama())
                .renkKodu(kategoriDto.getRenkKodu())
                .siraNo(kategoriDto.getSiraNo())
                .build();
        
        Kategori savedKategori = kategoriRepository.save(kategori);
        log.info("Kategori başarıyla oluşturuldu. ID: {}", savedKategori.getId());
        
        return convertToDto(savedKategori);
    }
    
    @Override
    public KategoriDto update(Long id, KategoriDto kategoriDto) {
        log.info("Kategori güncelleniyor. ID: {}", id);
        
        Kategori kategori = kategoriRepository.findByIdAndAktif(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kategori", id));
        
        // Ad kontrolü (kendisi hariç)
        if (!kategori.getAd().equals(kategoriDto.getAd()) && 
            kategoriRepository.existsByAd(kategoriDto.getAd())) {
            throw new BusinessException("Bu kategori adı zaten kullanımda: " + kategoriDto.getAd());
        }
        
        kategori.setAd(kategoriDto.getAd());
        kategori.setAciklama(kategoriDto.getAciklama());
        kategori.setRenkKodu(kategoriDto.getRenkKodu());
        kategori.setSiraNo(kategoriDto.getSiraNo());
        
        Kategori updatedKategori = kategoriRepository.save(kategori);
        log.info("Kategori başarıyla güncellendi. ID: {}", updatedKategori.getId());
        
        return convertToDto(updatedKategori);
    }
    
    @Override
    public void deleteById(Long id) {
        log.info("Kategori siliniyor. ID: {}", id);
        
        Kategori kategori = kategoriRepository.findByIdAndAktif(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kategori", id));
        
        // Soft delete
        kategori.setAktif(false);
        kategoriRepository.save(kategori);
        
        log.info("Kategori başarıyla silindi (soft delete). ID: {}", id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByAd(String ad) {
        return kategoriRepository.existsByAd(ad);
    }
    
        private KategoriDto convertToDto(Kategori kategori) {   
        // Mapper ile basic conversion
        KategoriDto dto = kategoriMapper.toDto(kategori);
        
        // Business logic: Talep sayısını hesapla
        long talepSayisi = kategori.getTalepler() != null ? 
                kategori.getTalepler().stream()
                        .filter(talep -> talep.getAktif())
                        .count() : 0;
        
        dto.setTalepSayisi(talepSayisi);
        return dto;
    }
} 