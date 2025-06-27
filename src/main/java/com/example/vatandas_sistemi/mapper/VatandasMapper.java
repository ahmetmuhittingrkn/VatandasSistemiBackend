package com.example.vatandas_sistemi.mapper;

import com.example.vatandas_sistemi.dto.VatandasCreateDto;
import com.example.vatandas_sistemi.dto.VatandasDto;
import com.example.vatandas_sistemi.entity.Vatandas;
import org.springframework.stereotype.Component;

@Component
public class VatandasMapper {
    
    public VatandasDto toDto(Vatandas vatandas) {
        if (vatandas == null) {
            return null;
        }
        
        return VatandasDto.builder()
                .id(vatandas.getId())
                .ad(vatandas.getAd())
                .soyad(vatandas.getSoyad())
                .email(vatandas.getEmail())
                .telefon(vatandas.getTelefon())
                .tcKimlikNo(vatandas.getTcKimlikNo())
                .adres(vatandas.getAdres())
                .sehir(vatandas.getSehir())
                .ilce(vatandas.getIlce())
                .mahalle(vatandas.getMahalle())
                .olusturmaTarihi(vatandas.getOlusturmaTarihi())
                .build();
    }
    
    public VatandasDto toDtoMinimal(Vatandas vatandas) {
        if (vatandas == null) {
            return null;
        }
        
        // Talep response'unda kullanılacak minimal veri
        return VatandasDto.builder()
                .id(vatandas.getId())
                .ad(vatandas.getAd())
                .soyad(vatandas.getSoyad())
                .email(vatandas.getEmail())
                .telefon(vatandas.getTelefon())
                .olusturmaTarihi(vatandas.getOlusturmaTarihi())
                .build();
    }
    
    public Vatandas toEntity(VatandasCreateDto createDto) {
        if (createDto == null) {
            return null;
        }
        
        return Vatandas.builder()
                .ad(createDto.getAd())
                .soyad(createDto.getSoyad())
                .email(createDto.getEmail())
                .telefon(createDto.getTelefon())
                .tcKimlikNo(createDto.getTcKimlikNo())
                .adres(createDto.getAdres())
                .sehir(createDto.getSehir())
                .ilce(createDto.getIlce())
                .mahalle(createDto.getMahalle())
                .build();
    }
    
    public void updateEntityFromDto(VatandasCreateDto updateDto, Vatandas vatandas) {
        if (updateDto == null || vatandas == null) {
            return;
        }
        
        vatandas.setAd(updateDto.getAd());
        vatandas.setSoyad(updateDto.getSoyad());
        vatandas.setEmail(updateDto.getEmail());
        vatandas.setTelefon(updateDto.getTelefon());
        vatandas.setTcKimlikNo(updateDto.getTcKimlikNo());
        vatandas.setAdres(updateDto.getAdres());
        vatandas.setSehir(updateDto.getSehir());
        vatandas.setIlce(updateDto.getIlce());
        vatandas.setMahalle(updateDto.getMahalle());
    }
} 