package com.example.vatandas_sistemi.dto;

import com.example.vatandas_sistemi.entity.enums.DurumEnum;
import com.example.vatandas_sistemi.entity.enums.OncelikEnum;
import com.example.vatandas_sistemi.entity.enums.TipEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TalepDto {
    
    private Long id;
    private String baslik;
    private String aciklama;
    private TipEnum tip;
    private DurumEnum durum;
    private OncelikEnum oncelik;
    private String takipNo;
    private Double konumLatitude;
    private Double konumLongitude;
    private String konumAciklama;
    
    private VatandasDto vatandas;
    private KategoriDto kategori;
    
    private List<TalepYorumDto> yorumlar;
    private List<TalepEkDto> ekler;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime olusturmaTarihi;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime guncellemeTarihi;
    
    // Convenience methods
    public String getTipAciklama() {
        return tip != null ? tip.getAciklama() : null;
    }
    
    public String getDurumAciklama() {
        return durum != null ? durum.getAciklama() : null;
    }
    
    public String getOncelikAciklama() {
        return oncelik != null ? oncelik.getAciklama() : null;
    }
} 