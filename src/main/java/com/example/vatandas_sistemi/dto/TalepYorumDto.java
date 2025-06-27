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
public class TalepYorumDto {
    
    private Long id;
    private String yorum;
    private String yorumYapan;
    private String yorumYapanTip;
    private Boolean gorunur;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime olusturmaTarihi;
} 