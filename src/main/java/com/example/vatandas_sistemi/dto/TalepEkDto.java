package com.example.vatandas_sistemi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TalepEkDto {
    
    private Long id;
    private String dosyaAdi;
    private String dosyaYolu;
    private String dosyaTipi;
    private Long dosyaBoyutu;
    private String aciklama;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime olusturmaTarihi;
    
    // Dosya boyutunu human readable format'a çeviren method
    public String getFormatlanmisDosyaBoyutu() {
        if (dosyaBoyutu == null) return "0 B";
        
        long bytes = dosyaBoyutu;
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        if (bytes < 1024 * 1024 * 1024) return String.format("%.1f MB", bytes / (1024.0 * 1024.0));
        
        return String.format("%.1f GB", bytes / (1024.0 * 1024.0 * 1024.0));
    }
} 