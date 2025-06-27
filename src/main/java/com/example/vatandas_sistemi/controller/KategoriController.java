package com.example.vatandas_sistemi.controller;

import com.example.vatandas_sistemi.dto.KategoriDto;
import com.example.vatandas_sistemi.service.KategoriService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/kategori")
@RequiredArgsConstructor
@Tag(name = "Kategori", description = "Talep kategorisi yönetimi API'leri")
public class KategoriController {
    
    private final KategoriService kategoriService;
    
    @PostMapping
    @Operation(summary = "Yeni kategori oluştur", description = "Yeni bir talep kategorisi oluşturur")
    public ResponseEntity<KategoriDto> createKategori(@Valid @RequestBody KategoriDto kategoriDto) {
        log.info("Yeni kategori oluşturma isteği: {}", kategoriDto.getAd());
        KategoriDto createdKategori = kategoriService.save(kategoriDto);
        return new ResponseEntity<>(createdKategori, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Kategori güncelle", description = "Mevcut kategori bilgilerini günceller")
    public ResponseEntity<KategoriDto> updateKategori(
            @Parameter(description = "Kategori ID") @PathVariable Long id,
            @Valid @RequestBody KategoriDto kategoriDto) {
        log.info("Kategori güncelleme isteği: {}", id);
        KategoriDto updatedKategori = kategoriService.update(id, kategoriDto);
        return ResponseEntity.ok(updatedKategori);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Kategori detay", description = "ID'ye göre kategori bilgilerini getirir")
    public ResponseEntity<KategoriDto> getKategori(
            @Parameter(description = "Kategori ID") @PathVariable Long id) {
        KategoriDto kategoriDto = kategoriService.findById(id);
        return ResponseEntity.ok(kategoriDto);
    }
    
    @GetMapping("/ad/{ad}")
    @Operation(summary = "Ad ile kategori ara", description = "Kategori adına göre kategori bulur")
    public ResponseEntity<KategoriDto> getKategoriByAd(
            @Parameter(description = "Kategori adı") @PathVariable String ad) {
        KategoriDto kategoriDto = kategoriService.findByAd(ad);
        return ResponseEntity.ok(kategoriDto);
    }
    
    @GetMapping("/aktif")
    @Operation(summary = "Aktif kategorileri listele", description = "Aktif durumda olan tüm kategorileri getirir")
    public ResponseEntity<List<KategoriDto>> getAktifKategoriler() {
        List<KategoriDto> kategoriler = kategoriService.findAllAktif();
        return ResponseEntity.ok(kategoriler);
    }
    
    @GetMapping
    @Operation(summary = "Tüm kategorileri listele", description = "Tüm kategorileri (aktif/pasif) getirir")
    public ResponseEntity<List<KategoriDto>> getAllKategoriler() {
        List<KategoriDto> kategoriler = kategoriService.findAll();
        return ResponseEntity.ok(kategoriler);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Kategori sil", description = "Kategori kaydını siler (soft delete)")
    public ResponseEntity<Void> deleteKategori(
            @Parameter(description = "Kategori ID") @PathVariable Long id) {
        log.info("Kategori silme isteği: {}", id);
        kategoriService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/check-ad")
    @Operation(summary = "Kategori adı kontrolü", description = "Kategori adının kullanımda olup olmadığını kontrol eder")
    public ResponseEntity<Boolean> checkKategoriAdExists(@RequestParam String ad) {
        boolean exists = kategoriService.existsByAd(ad);
        return ResponseEntity.ok(exists);
    }
} 