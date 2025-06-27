package com.example.vatandas_sistemi.repository;

import com.example.vatandas_sistemi.entity.Talep;
import com.example.vatandas_sistemi.entity.enums.DurumEnum;
import com.example.vatandas_sistemi.entity.enums.OncelikEnum;
import com.example.vatandas_sistemi.entity.enums.TipEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TalepRepository extends JpaRepository<Talep, Long> {
    
    Optional<Talep> findByTakipNo(String takipNo);
    
    List<Talep> findByVatandasId(Long vatandasId);
    
    Page<Talep> findByVatandasId(Long vatandasId, Pageable pageable);
    
    List<Talep> findByKategoriId(Long kategoriId);
    
    List<Talep> findByDurum(DurumEnum durum);
    
    List<Talep> findByTip(TipEnum tip);
    
    List<Talep> findByOncelik(OncelikEnum oncelik);
    
    @Query("SELECT t FROM Talep t WHERE t.aktif = true AND t.durum = :durum ORDER BY t.oncelik DESC, t.olusturmaTarihi DESC")
    List<Talep> findByDurumOrderByOncelikAndTarih(@Param("durum") DurumEnum durum);
    
    @Query("SELECT t FROM Talep t WHERE t.aktif = true AND t.tip = :tip AND t.durum = :durum")
    List<Talep> findByTipAndDurum(@Param("tip") TipEnum tip, @Param("durum") DurumEnum durum);
    
    @Query("SELECT t FROM Talep t WHERE t.aktif = true AND t.olusturmaTarihi BETWEEN :baslangic AND :bitis")
    List<Talep> findByTarihAraligi(@Param("baslangic") LocalDateTime baslangic, @Param("bitis") LocalDateTime bitis);
    
    @Query("SELECT COUNT(t) FROM Talep t WHERE t.aktif = true AND t.durum = :durum")
    long countByDurum(@Param("durum") DurumEnum durum);
    
    @Query("SELECT COUNT(t) FROM Talep t WHERE t.aktif = true AND t.tip = :tip")
    long countByTip(@Param("tip") TipEnum tip);
    
    @Query("SELECT t FROM Talep t WHERE t.aktif = true AND " +
           "(:baslik IS NULL OR LOWER(t.baslik) LIKE LOWER(CONCAT('%', :baslik, '%'))) AND " +
           "(:aciklama IS NULL OR LOWER(t.aciklama) LIKE LOWER(CONCAT('%', :aciklama, '%'))) AND " +
           "(:tip IS NULL OR t.tip = :tip) AND " +
           "(:durum IS NULL OR t.durum = :durum) AND " +
           "(:kategoriId IS NULL OR t.kategori.id = :kategoriId)")
    Page<Talep> findByAramaCriterialeri(
            @Param("baslik") String baslik,
            @Param("aciklama") String aciklama,
            @Param("tip") TipEnum tip,
            @Param("durum") DurumEnum durum,
            @Param("kategoriId") Long kategoriId,
            Pageable pageable);
} 