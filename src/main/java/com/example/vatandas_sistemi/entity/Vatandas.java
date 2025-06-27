package com.example.vatandas_sistemi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vatandas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vatandas extends BaseEntity {
    
    @Column(name = "ad", nullable = false, length = 50)
    private String ad;
    
    @Column(name = "soyad", nullable = false, length = 50)
    private String soyad;
    
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    
    @Column(name = "telefon", length = 15)
    private String telefon;
    
    @Column(name = "tc_kimlik_no", unique = true, length = 11)
    private String tcKimlikNo;
    
    @Column(name = "adres", length = 500)
    private String adres;
    
    @Column(name = "sehir", length = 50)
    private String sehir;
    
    @Column(name = "ilce", length = 50)
    private String ilce;
    
    @Column(name = "mahalle", length = 100)
    private String mahalle;
    
    @OneToMany(mappedBy = "vatandas", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Talep> talepler = new ArrayList<>();
} 