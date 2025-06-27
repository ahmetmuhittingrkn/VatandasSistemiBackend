package com.example.vatandas_sistemi.entity.enums;

public enum OncelikEnum {
    DUSUK("Düşük", 1),
    ORTA("Orta", 2),
    YUKSEK("Yüksek", 3),
    ACIL("Acil", 4);
    
    private final String aciklama;
    private final int seviye;
    
    OncelikEnum(String aciklama, int seviye) {
        this.aciklama = aciklama;
        this.seviye = seviye;
    }
    
    public String getAciklama() {
        return aciklama;
    }
    
    public int getSeviye() {
        return seviye;
    }
} 