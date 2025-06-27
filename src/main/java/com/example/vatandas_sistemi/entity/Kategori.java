package com.example.vatandas_sistemi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "kategori")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Kategori extends BaseEntity {
    
    @Column(name = "ad", nullable = false, unique = true, length = 100)
    private String ad;
    
    @Column(name = "aciklama", length = 500)
    private String aciklama;
    
    @Column(name = "renk_kodu", length = 7) // #FFFFFF formatında
    private String renkKodu;
    
    @Column(name = "sira_no")
    private Integer siraNo;
    
    @OneToMany(mappedBy = "kategori", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Talep> talepler = new ArrayList<>();
} 