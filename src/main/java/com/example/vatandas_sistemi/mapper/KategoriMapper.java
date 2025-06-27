package com.example.vatandas_sistemi.mapper;

import com.example.vatandas_sistemi.dto.KategoriCreateDto;
import com.example.vatandas_sistemi.dto.KategoriDto;
import com.example.vatandas_sistemi.entity.Kategori;
import org.springframework.stereotype.Component;

@Component
public class KategoriMapper {
    
    public KategoriDto toDto(Kategori kategori) {
        if (kategori == null) {
            return null;
        }
        
        return KategoriDto.builder()
                .id(kategori.getId())
                .ad(kategori.getAd())
                .aciklama(kategori.getAciklama())
                .renkKodu(kategori.getRenkKodu())
                .siraNo(kategori.getSiraNo())
                .olusturmaTarihi(kategori.getOlusturmaTarihi())
                .build();
    }
    
    public Kategori toEntity(KategoriCreateDto createDto) {
        if (createDto == null) {
            return null;
        }
        
        return Kategori.builder()
                .ad(createDto.getAd())
                .aciklama(createDto.getAciklama())
                .renkKodu(createDto.getRenkKodu())
                .siraNo(createDto.getSiraNo())
                .build();
    }
    
    public void updateEntityFromDto(KategoriCreateDto updateDto, Kategori kategori) {
        if (updateDto == null || kategori == null) {
            return;
        }
        
        kategori.setAd(updateDto.getAd());
        kategori.setAciklama(updateDto.getAciklama());
        kategori.setRenkKodu(updateDto.getRenkKodu());
        kategori.setSiraNo(updateDto.getSiraNo());
    }
} 