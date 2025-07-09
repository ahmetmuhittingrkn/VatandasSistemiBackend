package com.example.vatandas_sistemi.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    
    @Email(message = "Geçerli bir email adresi giriniz")
    @NotBlank(message = "Email adresi boş olamaz")
    private String email;
    
    @NotBlank(message = "Şifre boş olamaz")
    private String password;
} 