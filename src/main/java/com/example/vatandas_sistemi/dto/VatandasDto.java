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
public class VatandasDto {
    
    private Long id;
    private String ad;
    private String soyad;
    private String email;
    private String telefon;
    private String tcKimlikNo;
    private String adres;
    private String sehir;
    private String ilce;
    private String mahalle;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime olusturmaTarihi;
    
    // Tam ad için convenience method
    public String getTamAd() {
        return ad + " " + soyad;
    }
} 