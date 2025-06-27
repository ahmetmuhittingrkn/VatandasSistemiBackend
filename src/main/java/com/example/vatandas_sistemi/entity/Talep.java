package com.example.vatandas_sistemi.entity;

import com.example.vatandas_sistemi.entity.enums.DurumEnum;
import com.example.vatandas_sistemi.entity.enums.OncelikEnum;
import com.example.vatandas_sistemi.entity.enums.TipEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "talep")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Talep extends BaseEntity {
    
    @Column(name = "baslik", nullable = false, length = 200)
    private String baslik;
    
    @Column(name = "aciklama", nullable = false, columnDefinition = "TEXT")
    private String aciklama;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tip", nullable = false)
    private TipEnum tip;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "durum", nullable = false)
    @Builder.Default
    private DurumEnum durum = DurumEnum.BEKLEMEDE;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "oncelik")
    @Builder.Default
    private OncelikEnum oncelik = OncelikEnum.ORTA;
    
    @Column(name = "takip_no", unique = true, nullable = false, length = 20)
    private String takipNo;
    
    @Column(name = "konum_latitude")
    private Double konumLatitude;
    
    @Column(name = "konum_longitude")
    private Double konumLongitude;
    
    @Column(name = "konum_aciklama", length = 200)
    private String konumAciklama;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vatandas_id", nullable = false)
    private Vatandas vatandas;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kategori_id", nullable = false)
    private Kategori kategori;
    
    @OneToMany(mappedBy = "talep", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<TalepYorum> yorumlar = new ArrayList<>();
    
    @OneToMany(mappedBy = "talep", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<TalepEk> ekler = new ArrayList<>();
} 