package com.example.vatandas_sistemi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "talep_yorum")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TalepYorum extends BaseEntity {
    
    @Column(name = "yorum", nullable = false, columnDefinition = "TEXT")
    private String yorum;
    
    @Column(name = "yorum_yapan", nullable = false, length = 100)
    private String yorumYapan;
    
    @Column(name = "yorum_yapan_tip", length = 50) // VATANDAS, PERSONEL, SISTEM
    private String yorumYapanTip;
    
    @Column(name = "gorunur") // Vatandaşa görünür mü?
    @Builder.Default
    private Boolean gorunur = true;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "talep_id", nullable = false)
    private Talep talep;
} 