package com.example.vatandas_sistemi.repository;

import com.example.vatandas_sistemi.entity.TalepEk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TalepEkRepository extends JpaRepository<TalepEk, Long> {
    
    List<TalepEk> findByTalepId(Long talepId);
    
    @Query("SELECT te FROM TalepEk te WHERE te.talep.id = :talepId AND te.aktif = true ORDER BY te.olusturmaTarihi ASC")
    List<TalepEk> findByTalepIdOrderByTarih(@Param("talepId") Long talepId);
    
    @Query("SELECT COUNT(te) FROM TalepEk te WHERE te.talep.id = :talepId AND te.aktif = true")
    long countByTalepId(@Param("talepId") Long talepId);
    
    @Query("SELECT SUM(te.dosyaBoyutu) FROM TalepEk te WHERE te.talep.id = :talepId AND te.aktif = true")
    Long getTotalDosyaBoyutuByTalepId(@Param("talepId") Long talepId);
} 