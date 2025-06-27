package com.example.vatandas_sistemi.repository;

import com.example.vatandas_sistemi.entity.Vatandas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VatandasRepository extends JpaRepository<Vatandas, Long> {
    
    Optional<Vatandas> findByEmail(String email);
    
    Optional<Vatandas> findByTcKimlikNo(String tcKimlikNo);
    
    Optional<Vatandas> findByTelefon(String telefon);
    
    @Query("SELECT v FROM Vatandas v WHERE v.aktif = true AND (v.email = :email OR v.tcKimlikNo = :tcKimlikNo)")
    Optional<Vatandas> findByEmailOrTcKimlikNo(@Param("email") String email, @Param("tcKimlikNo") String tcKimlikNo);
    
    boolean existsByEmail(String email);
    
    boolean existsByTcKimlikNo(String tcKimlikNo);
} 