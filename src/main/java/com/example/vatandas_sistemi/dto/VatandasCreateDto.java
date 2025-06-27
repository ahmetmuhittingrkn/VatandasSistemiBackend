package com.example.vatandas_sistemi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VatandasCreateDto {
    
    @NotBlank(message = "Ad boş olamaz")
    @Size(min = 2, max = 50, message = "Ad 2-50 karakter arasında olmalı")
    private String ad;
    
    @NotBlank(message = "Soyad boş olamaz")
    @Size(min = 2, max = 50, message = "Soyad 2-50 karakter arasında olmalı")
    private String soyad;
    
    @NotBlank(message = "Email boş olamaz")
    @Email(message = "Geçerli bir email adresi giriniz")
    @Size(max = 100, message = "Email 100 karakterden uzun olamaz")
    private String email;
    
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Telefon numarası 10-15 rakam arasında olmalı")
    private String telefon;
    
    @Pattern(regexp = "^[0-9]{11}$", message = "TC Kimlik No 11 rakam olmalı")
    private String tcKimlikNo;
    
    @Size(max = 500, message = "Adres 500 karakterden uzun olamaz")
    private String adres;
    
    @Size(max = 50, message = "Şehir 50 karakterden uzun olamaz")
    private String sehir;
    
    @Size(max = 50, message = "İlçe 50 karakterden uzun olamaz")
    private String ilce;
    
    @Size(max = 100, message = "Mahalle 100 karakterden uzun olamaz")
    private String mahalle;
} 