package com.example.vatandas_sistemi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KategoriCreateDto {
    
    @NotBlank(message = "Kategori adı boş olamaz")
    @Size(min = 2, max = 100, message = "Kategori adı 2-100 karakter arasında olmalı")
    private String ad;
    
    @Size(max = 500, message = "Açıklama 500 karakterden uzun olamaz")
    private String aciklama;
    
    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Renk kodu #RRGGBB formatında olmalı")
    private String renkKodu;
    
    private Integer siraNo;
} 