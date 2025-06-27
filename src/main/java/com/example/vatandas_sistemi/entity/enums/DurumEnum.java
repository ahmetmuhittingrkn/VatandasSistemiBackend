package com.example.vatandas_sistemi.entity.enums;

public enum DurumEnum {
    BEKLEMEDE("Beklemede", "Başvuru alındı, değerlendiriliyor"),
    INCELENIYOR("İnceleniyor", "Başvuru ilgili birim tarafından inceleniyor"),
    COZULDU("Çözüldü", "Sorun çözüldü / Öneri değerlendirildi"),
    REDDEDILDI("Reddedildi", "Başvuru reddedildi"),
    IPTAL("İptal", "Başvuru iptal edildi");
    
    private final String aciklama;
    private final String detay;
    
    DurumEnum(String aciklama, String detay) {
        this.aciklama = aciklama;
        this.detay = detay;
    }
    
    public String getAciklama() {
        return aciklama;
    }
    
    public String getDetay() {
        return detay;
    }
} 