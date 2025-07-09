package com.example.vatandas_sistemi.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
    
    @NotBlank(message = "Ad boş olamaz")
    @Size(min = 2, max = 50, message = "Ad 2-50 karakter arasında olmalıdır")
    private String ad;
    
    @NotBlank(message = "Soyad boş olamaz")
    @Size(min = 2, max = 50, message = "Soyad 2-50 karakter arasında olmalıdır")
    private String soyad;
    
    @Email(message = "Geçerli bir email adresi giriniz")
    @NotBlank(message = "Email adresi boş olamaz")
    private String email;
    
    @NotBlank(message = "Şifre boş olamaz")
    @Size(min = 6, max = 100, message = "Şifre en az 6 karakter olmalıdır")
    private String password;
    
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Telefon numarası 10-11 haneli olmalıdır")
    private String telefon;
    
    @Pattern(regexp = "^[0-9]{11}$", message = "TC Kimlik No 11 haneli olmalıdır")
    private String tcKimlikNo;
    
    @Size(max = 500, message = "Adres 500 karakterden fazla olamaz")
    private String adres;
    
    @Size(max = 50, message = "Şehir 50 karakterden fazla olamaz")
    private String sehir;
    
    @Size(max = 50, message = "İlçe 50 karakterden fazla olamaz")
    private String ilce;
    
    @Size(max = 100, message = "Mahalle 100 karakterden fazla olamaz")
    private String mahalle;
} 