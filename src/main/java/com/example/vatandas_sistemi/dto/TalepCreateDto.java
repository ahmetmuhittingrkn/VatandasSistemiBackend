package com.example.vatandas_sistemi.dto;

import com.example.vatandas_sistemi.entity.enums.OncelikEnum;
import com.example.vatandas_sistemi.entity.enums.TipEnum;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TalepCreateDto {
    
    @NotBlank(message = "Başlık boş olamaz")
    @Size(min = 5, max = 200, message = "Başlık 5-200 karakter arasında olmalı")
    private String baslik;
    
    @NotBlank(message = "Açıklama boş olamaz")
    @Size(min = 10, max = 2000, message = "Açıklama 10-2000 karakter arasında olmalı")
    private String aciklama;
    
    @NotNull(message = "Tip seçilmeli")
    private TipEnum tip;
    
    @Builder.Default
    private OncelikEnum oncelik = OncelikEnum.ORTA;
    
    @NotNull(message = "Vatandaş ID'si gerekli")
    private Long vatandasId;
    
    @NotNull(message = "Kategori seçilmeli")
    private Long kategoriId;
    
    @DecimalMin(value = "-90.0", message = "Geçersiz latitude değeri")
    @DecimalMax(value = "90.0", message = "Geçersiz latitude değeri")
    private Double konumLatitude;
    
    @DecimalMin(value = "-180.0", message = "Geçersiz longitude değeri")
    @DecimalMax(value = "180.0", message = "Geçersiz longitude değeri")
    private Double konumLongitude;
    
    @Size(max = 200, message = "Konum açıklaması 200 karakterden uzun olamaz")
    private String konumAciklama;
} 