package com.example.vatandas_sistemi.repository;

import com.example.vatandas_sistemi.entity.TalepYorum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TalepYorumRepository extends JpaRepository<TalepYorum, Long> {
    
    List<TalepYorum> findByTalepId(Long talepId);
    
    @Query("SELECT ty FROM TalepYorum ty WHERE ty.talep.id = :talepId AND ty.aktif = true ORDER BY ty.olusturmaTarihi ASC")
    List<TalepYorum> findByTalepIdOrderByTarih(@Param("talepId") Long talepId);
    
    @Query("SELECT ty FROM TalepYorum ty WHERE ty.talep.id = :talepId AND ty.aktif = true AND ty.gorunur = true ORDER BY ty.olusturmaTarihi ASC")
    List<TalepYorum> findGorunurYorumlarByTalepId(@Param("talepId") Long talepId);
    
    @Query("SELECT COUNT(ty) FROM TalepYorum ty WHERE ty.talep.id = :talepId AND ty.aktif = true")
    long countByTalepId(@Param("talepId") Long talepId);
} 