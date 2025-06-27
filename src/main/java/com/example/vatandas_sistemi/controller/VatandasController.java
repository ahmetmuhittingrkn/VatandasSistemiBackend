package com.example.vatandas_sistemi.controller;

import com.example.vatandas_sistemi.dto.VatandasCreateDto;
import com.example.vatandas_sistemi.dto.VatandasDto;
import com.example.vatandas_sistemi.service.VatandasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/vatandas")
@RequiredArgsConstructor
@Tag(name = "Vatandaş", description = "Vatandaş yönetimi API'leri")
public class VatandasController {
    
    private final VatandasService vatandasService;
    
    @PostMapping
    @Operation(summary = "Yeni vatandaş kayıt", description = "Yeni bir vatandaş kaydı oluşturur")
    public ResponseEntity<VatandasDto> createVatandas(@Valid @RequestBody VatandasCreateDto createDto) {
        log.info("Yeni vatandaş oluşturma isteği: {}", createDto.getEmail());
        VatandasDto vatandasDto = vatandasService.save(createDto);
        return new ResponseEntity<>(vatandasDto, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Vatandaş güncelle", description = "Mevcut vatandaş bilgilerini günceller")
    public ResponseEntity<VatandasDto> updateVatandas(
            @Parameter(description = "Vatandaş ID") @PathVariable Long id,
            @Valid @RequestBody VatandasCreateDto updateDto) {
        log.info("Vatandaş güncelleme isteği: {}", id);
        VatandasDto vatandasDto = vatandasService.update(id, updateDto);
        return ResponseEntity.ok(vatandasDto);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Vatandaş detay", description = "ID'ye göre vatandaş bilgilerini getirir")
    public ResponseEntity<VatandasDto> getVatandas(
            @Parameter(description = "Vatandaş ID") @PathVariable Long id) {
        VatandasDto vatandasDto = vatandasService.findById(id);
        return ResponseEntity.ok(vatandasDto);
    }
    
    @GetMapping("/email/{email}")
    @Operation(summary = "Email ile vatandaş ara", description = "Email adresine göre vatandaş bulur")
    public ResponseEntity<VatandasDto> getVatandasByEmail(
            @Parameter(description = "Email adresi") @PathVariable String email) {
        VatandasDto vatandasDto = vatandasService.findByEmail(email);
        return ResponseEntity.ok(vatandasDto);
    }
    
    @GetMapping("/tc/{tcKimlikNo}")
    @Operation(summary = "TC Kimlik No ile vatandaş ara", description = "TC Kimlik numarasına göre vatandaş bulur")
    public ResponseEntity<VatandasDto> getVatandasByTcKimlikNo(
            @Parameter(description = "TC Kimlik No") @PathVariable String tcKimlikNo) {
        VatandasDto vatandasDto = vatandasService.findByTcKimlikNo(tcKimlikNo);
        return ResponseEntity.ok(vatandasDto);
    }
    
    @GetMapping
    @Operation(summary = "Tüm vatandaşları listele", description = "Sayfalanmış vatandaş listesi")
    public ResponseEntity<Page<VatandasDto>> getAllVatandas(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<VatandasDto> vatandaslar = vatandasService.findAll(pageable);
        return ResponseEntity.ok(vatandaslar);
    }
    
    @GetMapping("/list")
    @Operation(summary = "Tüm vatandaşları listele (basit)", description = "Basit vatandaş listesi")
    public ResponseEntity<List<VatandasDto>> getAllVatandasList() {
        List<VatandasDto> vatandaslar = vatandasService.findAll();
        return ResponseEntity.ok(vatandaslar);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Vatandaş sil", description = "Vatandaş kaydını siler (soft delete)")
    public ResponseEntity<Void> deleteVatandas(
            @Parameter(description = "Vatandaş ID") @PathVariable Long id) {
        log.info("Vatandaş silme isteği: {}", id);
        vatandasService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/check-email")
    @Operation(summary = "Email kontrolü", description = "Email adresinin kullanımda olup olmadığını kontrol eder")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = vatandasService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
    
    @GetMapping("/check-tc")
    @Operation(summary = "TC Kimlik No kontrolü", description = "TC Kimlik No'nun kullanımda olup olmadığını kontrol eder")
    public ResponseEntity<Boolean> checkTcKimlikNoExists(@RequestParam String tcKimlikNo) {
        boolean exists = vatandasService.existsByTcKimlikNo(tcKimlikNo);
        return ResponseEntity.ok(exists);
    }
} 