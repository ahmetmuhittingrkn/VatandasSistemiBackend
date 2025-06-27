package com.example.vatandas_sistemi.entity.enums;

public enum TipEnum {
    SIKAYET("Şikayet"),
    ONERI("Öneri");
    
    private final String aciklama;
    
    TipEnum(String aciklama) {
        this.aciklama = aciklama;
    }
    
    public String getAciklama() {
        return aciklama;
    }
} 