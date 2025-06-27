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
public class KategoriDto {
    
    private Long id;
    private String ad;
    private String aciklama;
    private String renkKodu;
    private Integer siraNo;
    private Long talepSayisi;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime olusturmaTarihi;
} 