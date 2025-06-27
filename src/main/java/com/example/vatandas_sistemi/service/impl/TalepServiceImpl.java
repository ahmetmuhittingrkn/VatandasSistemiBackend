package com.example.vatandas_sistemi.service.impl;

import com.example.vatandas_sistemi.dto.*;
import com.example.vatandas_sistemi.entity.*;
import com.example.vatandas_sistemi.entity.enums.DurumEnum;
import com.example.vatandas_sistemi.entity.enums.OncelikEnum;
import com.example.vatandas_sistemi.entity.enums.TipEnum;
import com.example.vatandas_sistemi.exception.BusinessException;
import com.example.vatandas_sistemi.exception.ResourceNotFoundException;
import com.example.vatandas_sistemi.mapper.TalepMapper;
import com.example.vatandas_sistemi.repository.*;
import com.example.vatandas_sistemi.service.TalepService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TalepServiceImpl implements TalepService {
    
    private final TalepRepository talepRepository;
    private final VatandasRepository vatandasRepository;
    private final KategoriRepository kategoriRepository;
    private final TalepYorumRepository talepYorumRepository;
    private final TalepEkRepository talepEkRepository;
    private final TalepMapper talepMapper;
    
    @Override
    public TalepDto save(TalepCreateDto createDto) {
        log.info("Yeni talep oluşturuluyor: {}", createDto.getBaslik());
        
        // Vatandaş kontrolü
        Vatandas vatandas = vatandasRepository.findById(createDto.getVatandasId())
                .orElseThrow(() -> new ResourceNotFoundException("Vatandaş", createDto.getVatandasId()));
        
        // Kategori kontrolü
        Kategori kategori = kategoriRepository.findByIdAndAktif(createDto.getKategoriId())
                .orElseThrow(() -> new ResourceNotFoundException("Kategori", createDto.getKategoriId()));
        
        String takipNo = generateTakipNo();
        
        Talep talep = Talep.builder()
                .baslik(createDto.getBaslik())
                .aciklama(createDto.getAciklama())
                .tip(createDto.getTip())
                .oncelik(createDto.getOncelik())
                .takipNo(takipNo)
                .konumLatitude(createDto.getKonumLatitude())
                .konumLongitude(createDto.getKonumLongitude())
                .konumAciklama(createDto.getKonumAciklama())
                .vatandas(vatandas)
                .kategori(kategori)
                .durum(DurumEnum.BEKLEMEDE)
                .build();
        
        Talep savedTalep = talepRepository.save(talep);
        
        // Sistem yorumu ekle
        addSystemComment(savedTalep, "Başvuru alındı ve değerlendirme sürecine başlandı.");
        
        log.info("Talep başarıyla oluşturuldu. ID: {}, Takip No: {}", savedTalep.getId(), takipNo);
        
        return convertToDto(savedTalep);
    }
    
    @Override
    @Transactional(readOnly = true)
    public TalepDto findById(Long id) {
        Talep talep = talepRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Talep", id));
        return convertToDto(talep);
    }
    
    @Override
    @Transactional(readOnly = true)
    public TalepDto findByTakipNo(String takipNo) {
        Talep talep = talepRepository.findByTakipNo(takipNo)
                .orElseThrow(() -> new ResourceNotFoundException("Takip numarası bulunamadı: " + takipNo));
        return convertToDto(talep);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TalepDto> findByVatandasId(Long vatandasId) {
        return talepRepository.findByVatandasId(vatandasId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<TalepDto> findByVatandasId(Long vatandasId, Pageable pageable) {
        return talepRepository.findByVatandasId(vatandasId, pageable)
                .map(this::convertToDto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TalepDto> findByKategoriId(Long kategoriId) {
        return talepRepository.findByKategoriId(kategoriId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TalepDto> findByDurum(DurumEnum durum) {
        return talepRepository.findByDurumOrderByOncelikAndTarih(durum).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TalepDto> findByTip(TipEnum tip) {
        return talepRepository.findByTip(tip).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<TalepDto> findAll(Pageable pageable) {
        return talepRepository.findAll(pageable)
                .map(this::convertToDto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<TalepDto> search(String baslik, String aciklama, TipEnum tip, 
                                DurumEnum durum, Long kategoriId, Pageable pageable) {
        return talepRepository.findByAramaCriterialeri(baslik, aciklama, tip, durum, kategoriId, pageable)
                .map(this::convertToDto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TalepDto> findByTarihAraligi(LocalDateTime baslangic, LocalDateTime bitis) {
        return talepRepository.findByTarihAraligi(baslangic, bitis).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public TalepDto updateDurum(Long id, DurumEnum yeniDurum, String aciklama) {
        log.info("Talep durumu güncelleniyor. ID: {}, Yeni Durum: {}", id, yeniDurum);
        
        Talep talep = talepRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Talep", id));
        
        DurumEnum eskiDurum = talep.getDurum();
        talep.setDurum(yeniDurum);
        
        Talep updatedTalep = talepRepository.save(talep);
        
        // Durum değişikliği yorumu ekle
        String yorumMetni = String.format("Durum değiştirildi: %s → %s", 
                eskiDurum.getAciklama(), yeniDurum.getAciklama());
        if (aciklama != null && !aciklama.trim().isEmpty()) {
            yorumMetni += "\nAçıklama: " + aciklama;
        }
        addSystemComment(updatedTalep, yorumMetni);
        
        log.info("Talep durumu başarıyla güncellendi. ID: {}", id);
        
        return convertToDto(updatedTalep);
    }
    
    @Override
    public TalepDto updateOncelik(Long id, OncelikEnum yeniOncelik) {
        log.info("Talep önceliği güncelleniyor. ID: {}, Yeni Öncelik: {}", id, yeniOncelik);
        
        Talep talep = talepRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Talep", id));
        
        OncelikEnum eskiOncelik = talep.getOncelik();
        talep.setOncelik(yeniOncelik);
        
        Talep updatedTalep = talepRepository.save(talep);
        
        // Öncelik değişikliği yorumu ekle
        addSystemComment(updatedTalep, String.format("Öncelik değiştirildi: %s → %s", 
                eskiOncelik.getAciklama(), yeniOncelik.getAciklama()));
        
        log.info("Talep önceliği başarıyla güncellendi. ID: {}", id);
        
        return convertToDto(updatedTalep);
    }
    
    @Override
    public void deleteById(Long id) {
        log.info("Talep siliniyor. ID: {}", id);
        
        Talep talep = talepRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Talep", id));
        
        // Soft delete
        talep.setAktif(false);
        talepRepository.save(talep);
        
        log.info("Talep başarıyla silindi (soft delete). ID: {}", id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Map<DurumEnum, Long> getDurumIstatistikleri() {
        Map<DurumEnum, Long> istatistikler = new HashMap<>();
        for (DurumEnum durum : DurumEnum.values()) {
            long count = talepRepository.countByDurum(durum);
            istatistikler.put(durum, count);
        }
        return istatistikler;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Map<TipEnum, Long> getTipIstatistikleri() {
        Map<TipEnum, Long> istatistikler = new HashMap<>();
        for (TipEnum tip : TipEnum.values()) {
            long count = talepRepository.countByTip(tip);
            istatistikler.put(tip, count);
        }
        return istatistikler;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> getAylikIstatistikler(int yil) {
        // Bu method için daha detaylı implementasyon gerekli
        // Şimdilik basit bir implementasyon
        return new HashMap<>();
    }
    
    @Override
    public String generateTakipNo() {
        String prefix = "TAL";
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomSuffix = String.format("%04d", new Random().nextInt(10000));
        return prefix + timestamp + randomSuffix;
    }
    
    private void addSystemComment(Talep talep, String yorum) {
        TalepYorum sistemYorum = TalepYorum.builder()
                .yorum(yorum)
                .yorumYapan("SİSTEM")
                .yorumYapanTip("SISTEM")
                .gorunur(true)
                .talep(talep)
                .build();
        
        talepYorumRepository.save(sistemYorum);
    }
    
    private TalepDto convertToDto(Talep talep) {
        // Yorumları getir
        List<TalepYorumDto> yorumlar = talepYorumRepository.findGorunurYorumlarByTalepId(talep.getId()).stream()
                .map(talepMapper::yorumToDto)
                .collect(Collectors.toList());
        
        // Ekleri getir
        List<TalepEkDto> ekler = talepEkRepository.findByTalepIdOrderByTarih(talep.getId()).stream()
                .map(talepMapper::ekToDto)
                .collect(Collectors.toList());
        
        return talepMapper.toDtoWithDetails(talep, 
                talepYorumRepository.findGorunurYorumlarByTalepId(talep.getId()),
                talepEkRepository.findByTalepIdOrderByTarih(talep.getId()));
    }
    
    
}