package com.example.vatandas_sistemi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "talep_ek")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TalepEk extends BaseEntity {
    
    @Column(name = "dosya_adi", nullable = false, length = 255)
    private String dosyaAdi;
    
    @Column(name = "dosya_yolu", nullable = false, length = 500)
    private String dosyaYolu;
    
    @Column(name = "dosya_tipi", length = 100)
    private String dosyaTipi;
    
    @Column(name = "dosya_boyutu")
    private Long dosyaBoyutu;
    
    @Column(name = "aciklama", length = 200)
    private String aciklama;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "talep_id", nullable = false)
    private Talep talep;
} 