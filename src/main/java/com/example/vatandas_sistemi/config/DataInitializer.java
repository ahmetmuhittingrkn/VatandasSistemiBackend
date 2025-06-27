package com.example.vatandas_sistemi.config;

import com.example.vatandas_sistemi.entity.Kategori;
import com.example.vatandas_sistemi.repository.KategoriRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final KategoriRepository kategoriRepository;
    
    @Override
    public void run(String... args) throws Exception {
        initializeKategoriler();
    }
    
    private void initializeKategoriler() {
        log.info("Başlangıç kategorileri kontrol ediliyor...");
        
        if (kategoriRepository.count() == 0) {
            log.info("Varsayılan kategoriler oluşturuluyor...");
            
            List<Kategori> kategoriler = List.of(
                    Kategori.builder()
                            .ad("Çevre ve Temizlik")
                            .aciklama("Çevre kirliliği, temizlik sorunları, atık yönetimi")
                            .renkKodu("#4CAF50")
                            .siraNo(1)
                            .build(),
                    
                    Kategori.builder()
                            .ad("Ulaşım")
                            .aciklama("Yol, kaldırım, trafik, otobüs durağı sorunları")
                            .renkKodu("#2196F3")
                            .siraNo(2)
                            .build(),
                    
                    Kategori.builder()
                            .ad("Park ve Bahçe")
                            .aciklama("Yeşil alan, park, oyun alanları")
                            .renkKodu("#8BC34A")
                            .siraNo(3)
                            .build(),
                    
                    Kategori.builder()
                            .ad("Altyapı")
                            .aciklama("Su, kanalizasyon, elektrik, doğalgaz sorunları")
                            .renkKodu("#FF9800")
                            .siraNo(4)
                            .build(),
                    
                    Kategori.builder()
                            .ad("Sosyal Hizmetler")
                            .aciklama("Eğitim, sağlık, sosyal yardım hizmetleri")
                            .renkKodu("#9C27B0")
                            .siraNo(5)
                            .build(),
                    
                    Kategori.builder()
                            .ad("İmar ve Planlama")
                            .aciklama("Yapı ruhsatı, imar sorunları, kaçak yapılaşma")
                            .renkKodu("#607D8B")
                            .siraNo(6)
                            .build(),
                    
                    Kategori.builder()
                            .ad("Kültür ve Sanat")
                            .aciklama("Kültürel etkinlikler, sanat aktiviteleri önerileri")
                            .renkKodu("#E91E63")
                            .siraNo(7)
                            .build(),
                    
                    Kategori.builder()
                            .ad("Spor")
                            .aciklama("Spor tesisleri, etkinlikleri, öneriler")
                            .renkKodu("#F44336")
                            .siraNo(8)
                            .build(),
                    
                    Kategori.builder()
                            .ad("Zabıta")
                            .aciklama("Güvenlik, asayiş, denetim sorunları")
                            .renkKodu("#795548")
                            .siraNo(9)
                            .build(),
                    
                    Kategori.builder()
                            .ad("Diğer")
                            .aciklama("Yukarıdaki kategorilere girmeyen diğer konular")
                            .renkKodu("#9E9E9E")
                            .siraNo(10)
                            .build()
            );
            
            kategoriRepository.saveAll(kategoriler);
            log.info("{} adet varsayılan kategori oluşturuldu", kategoriler.size());
        } else {
            log.info("Kategoriler zaten mevcut, başlangıç verisi eklenmedi");
        }
    }
} 