package com.example.vatandas_sistemi.controller;

import com.example.vatandas_sistemi.dto.TalepCreateDto;
import com.example.vatandas_sistemi.dto.TalepDto;
import com.example.vatandas_sistemi.entity.enums.DurumEnum;
import com.example.vatandas_sistemi.entity.enums.OncelikEnum;
import com.example.vatandas_sistemi.entity.enums.TipEnum;
import com.example.vatandas_sistemi.service.TalepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/talep")
@RequiredArgsConstructor
@Tag(name = "Talep", description = "Şikayet ve öneri talebi yönetimi API'leri")
public class TalepController {
    
    private final TalepService talepService;
    
    @PostMapping
    @Operation(summary = "Yeni talep oluştur", description = "Yeni şikayet veya öneri talebi oluşturur")
    public ResponseEntity<TalepDto> createTalep(@Valid @RequestBody TalepCreateDto createDto) {
        log.info("Yeni talep oluşturma isteği: {}", createDto.getBaslik());
        TalepDto talepDto = talepService.save(createDto);
        return new ResponseEntity<>(talepDto, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Talep detay", description = "ID'ye göre talep detaylarını getirir")
    public ResponseEntity<TalepDto> getTalep(
            @Parameter(description = "Talep ID") @PathVariable Long id) {
        TalepDto talepDto = talepService.findById(id);
        return ResponseEntity.ok(talepDto);
    }
    
    @GetMapping("/takip/{takipNo}")
    @Operation(summary = "Takip numarası ile talep sorgula", description = "Takip numarasına göre talep bulur")
    public ResponseEntity<TalepDto> getTalepByTakipNo(
            @Parameter(description = "Takip numarası") @PathVariable String takipNo) {
        TalepDto talepDto = talepService.findByTakipNo(takipNo);
        return ResponseEntity.ok(talepDto);
    }
    
    @GetMapping("/vatandas/{vatandasId}")
    @Operation(summary = "Vatandaş talepler", description = "Belirli bir vatandaşın tüm taleplerini getirir")
    public ResponseEntity<List<TalepDto>> getTaleplerByVatandas(
            @Parameter(description = "Vatandaş ID") @PathVariable Long vatandasId) {
        List<TalepDto> talepler = talepService.findByVatandasId(vatandasId);
        return ResponseEntity.ok(talepler);
    }
    
    @GetMapping("/vatandas/{vatandasId}/sayfalanmis")
    @Operation(summary = "Vatandaş talepler (sayfalanmış)", description = "Belirli bir vatandaşın taleplerini sayfalanmış olarak getirir")
    public ResponseEntity<Page<TalepDto>> getTaleplerByVatandasPaged(
            @Parameter(description = "Vatandaş ID") @PathVariable Long vatandasId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<TalepDto> talepler = talepService.findByVatandasId(vatandasId, pageable);
        return ResponseEntity.ok(talepler);
    }
    
    @GetMapping("/kategori/{kategoriId}")
    @Operation(summary = "Kategori talepler", description = "Belirli bir kategorideki tüm talepleri getirir")
    public ResponseEntity<List<TalepDto>> getTaleplerByKategori(
            @Parameter(description = "Kategori ID") @PathVariable Long kategoriId) {
        List<TalepDto> talepler = talepService.findByKategoriId(kategoriId);
        return ResponseEntity.ok(talepler);
    }
    
    @GetMapping("/durum/{durum}")
    @Operation(summary = "Durum göre talepler", description = "Belirli bir durumdaki tüm talepleri getirir")
    public ResponseEntity<List<TalepDto>> getTaleplerByDurum(
            @Parameter(description = "Talep durumu") @PathVariable DurumEnum durum) {
        List<TalepDto> talepler = talepService.findByDurum(durum);
        return ResponseEntity.ok(talepler);
    }
    
    @GetMapping("/tip/{tip}")
    @Operation(summary = "Tip göre talepler", description = "Belirli bir tipteki (şikayet/öneri) tüm talepleri getirir")
    public ResponseEntity<List<TalepDto>> getTaleplerByTip(
            @Parameter(description = "Talep tipi") @PathVariable TipEnum tip) {
        List<TalepDto> talepler = talepService.findByTip(tip);
        return ResponseEntity.ok(talepler);
    }
    
    @GetMapping
    @Operation(summary = "Tüm talepleri listele", description = "Sayfalanmış talep listesi")
    public ResponseEntity<Page<TalepDto>> getAllTalepler(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<TalepDto> talepler = talepService.findAll(pageable);
        return ResponseEntity.ok(talepler);
    }
    
    @GetMapping("/ara")
    @Operation(summary = "Talep arama", description = "Çeşitli kriterlere göre talep araması yapar")
    public ResponseEntity<Page<TalepDto>> searchTalepler(
            @RequestParam(required = false) String baslik,
            @RequestParam(required = false) String aciklama,
            @RequestParam(required = false) TipEnum tip,
            @RequestParam(required = false) DurumEnum durum,
            @RequestParam(required = false) Long kategoriId,
            @PageableDefault(size = 20) Pageable pageable) {
        
        Page<TalepDto> talepler = talepService.search(baslik, aciklama, tip, durum, kategoriId, pageable);
        return ResponseEntity.ok(talepler);
    }
    
    @GetMapping("/tarih-araligi")
    @Operation(summary = "Tarih aralığında talep ara", description = "Belirli tarih aralığındaki talepleri getirir")
    public ResponseEntity<List<TalepDto>> getTaleplerByTarihAraligi(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime baslangic,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bitis) {
        
        List<TalepDto> talepler = talepService.findByTarihAraligi(baslangic, bitis);
        return ResponseEntity.ok(talepler);
    }
    
    @PutMapping("/{id}/durum")
    @Operation(summary = "Talep durumunu güncelle", description = "Talebin durumunu günceller")
    public ResponseEntity<TalepDto> updateTalepDurum(
            @Parameter(description = "Talep ID") @PathVariable Long id,
            @RequestParam DurumEnum yeniDurum,
            @RequestParam(required = false) String aciklama) {
        
        log.info("Talep durumu güncelleme isteği - ID: {}, Yeni Durum: {}", id, yeniDurum);
        TalepDto talepDto = talepService.updateDurum(id, yeniDurum, aciklama);
        return ResponseEntity.ok(talepDto);
    }
    
    @PutMapping("/{id}/oncelik")
    @Operation(summary = "Talep önceliğini güncelle", description = "Talebin önceliğini günceller")
    public ResponseEntity<TalepDto> updateTalepOncelik(
            @Parameter(description = "Talep ID") @PathVariable Long id,
            @RequestParam OncelikEnum yeniOncelik) {
        
        log.info("Talep önceliği güncelleme isteği - ID: {}, Yeni Öncelik: {}", id, yeniOncelik);
        TalepDto talepDto = talepService.updateOncelik(id, yeniOncelik);
        return ResponseEntity.ok(talepDto);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Talep sil", description = "Talep kaydını siler (soft delete)")
    public ResponseEntity<Void> deleteTalep(
            @Parameter(description = "Talep ID") @PathVariable Long id) {
        log.info("Talep silme isteği: {}", id);
        talepService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/istatistik/durum")
    @Operation(summary = "Durum istatistikleri", description = "Talep durumlarına göre istatistikleri getirir")
    public ResponseEntity<Map<DurumEnum, Long>> getDurumIstatistikleri() {
        Map<DurumEnum, Long> istatistikler = talepService.getDurumIstatistikleri();
        return ResponseEntity.ok(istatistikler);
    }
    
    @GetMapping("/istatistik/tip")
    @Operation(summary = "Tip istatistikleri", description = "Talep tiplerine göre istatistikleri getirir")
    public ResponseEntity<Map<TipEnum, Long>> getTipIstatistikleri() {
        Map<TipEnum, Long> istatistikler = talepService.getTipIstatistikleri();
        return ResponseEntity.ok(istatistikler);
    }
    
    @GetMapping("/istatistik/aylik/{yil}")
    @Operation(summary = "Aylık istatistikler", description = "Belirli bir yıl için aylık talep istatistiklerini getirir")
    public ResponseEntity<Map<String, Long>> getAylikIstatistikler(
            @Parameter(description = "Yıl") @PathVariable int yil) {
        Map<String, Long> istatistikler = talepService.getAylikIstatistikler(yil);
        return ResponseEntity.ok(istatistikler);
    }
    
    @GetMapping("/takip-no-olustur")
    @Operation(summary = "Takip numarası oluştur", description = "Yeni bir takip numarası oluşturur")
    public ResponseEntity<String> generateTakipNo() {
        String takipNo = talepService.generateTakipNo();
        return ResponseEntity.ok(takipNo);
    }
} 