package com.example.vatandas_sistemi.controller;

import com.example.vatandas_sistemi.dto.auth.JwtResponseDto;
import com.example.vatandas_sistemi.dto.auth.LoginRequestDto;
import com.example.vatandas_sistemi.dto.auth.RegisterRequestDto;
import com.example.vatandas_sistemi.entity.Vatandas;
import com.example.vatandas_sistemi.exception.BusinessException;
import com.example.vatandas_sistemi.repository.VatandasRepository;
import com.example.vatandas_sistemi.security.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Kullanıcı kimlik doğrulama ve yetkilendirme API'leri")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final VatandasRepository vatandasRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    @Operation(summary = "Kullanıcı girişi", description = "Email ve şifre ile giriş yapar, JWT token döner")
    public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        log.info("Login attempt for user: {}", loginRequest.getEmail());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Vatandas vatandas = (Vatandas) userDetails;

            String accessToken = jwtUtils.generateAccessToken(userDetails);
            String refreshToken = jwtUtils.generateRefreshToken(userDetails);

            JwtResponseDto.UserInfoDto userInfo = JwtResponseDto.UserInfoDto.builder()
                    .id(vatandas.getId())
                    .ad(vatandas.getAd())
                    .soyad(vatandas.getSoyad())
                    .email(vatandas.getEmail())
                    .role(vatandas.getRole())
                    .sonGirisTarihi(LocalDateTime.now())
                    .build();

            JwtResponseDto response = JwtResponseDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .tokenType("Bearer")
                    .expiresAt(LocalDateTime.now().plusDays(1)) // 24 saat
                    .user(userInfo)
                    .build();

            log.info("User logged in successfully: {}", loginRequest.getEmail());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Login failed for user: {}", loginRequest.getEmail(), e);
            throw new BusinessException("Email veya şifre hatalı");
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Kullanıcı kaydı", description = "Yeni kullanıcı kaydı oluşturur")
    public ResponseEntity<JwtResponseDto> register(@Valid @RequestBody RegisterRequestDto registerRequest) {
        log.info("Registration attempt for user: {}", registerRequest.getEmail());

        // Email kontrolü
        if (vatandasRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BusinessException("Bu email adresi zaten kullanımda: " + registerRequest.getEmail());
        }

        // TC Kimlik No kontrolü
        if (registerRequest.getTcKimlikNo() != null && 
            vatandasRepository.existsByTcKimlikNo(registerRequest.getTcKimlikNo())) {
            throw new BusinessException("Bu TC Kimlik No zaten kullanımda: " + registerRequest.getTcKimlikNo());
        }

        try {
            // Yeni vatandaş oluştur
            Vatandas vatandas = Vatandas.builder()
                    .ad(registerRequest.getAd())
                    .soyad(registerRequest.getSoyad())
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .telefon(registerRequest.getTelefon())
                    .tcKimlikNo(registerRequest.getTcKimlikNo())
                    .adres(registerRequest.getAdres())
                    .sehir(registerRequest.getSehir())
                    .ilce(registerRequest.getIlce())
                    .mahalle(registerRequest.getMahalle())
                    .role("VATANDAS")
                    .build();

            Vatandas savedVatandas = vatandasRepository.save(vatandas);

            // JWT token'ları oluştur
            String accessToken = jwtUtils.generateAccessToken(savedVatandas);
            String refreshToken = jwtUtils.generateRefreshToken(savedVatandas);

            JwtResponseDto.UserInfoDto userInfo = JwtResponseDto.UserInfoDto.builder()
                    .id(savedVatandas.getId())
                    .ad(savedVatandas.getAd())
                    .soyad(savedVatandas.getSoyad())
                    .email(savedVatandas.getEmail())
                    .role(savedVatandas.getRole())
                    .sonGirisTarihi(LocalDateTime.now())
                    .build();

            JwtResponseDto response = JwtResponseDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .tokenType("Bearer")
                    .expiresAt(LocalDateTime.now().plusDays(1))
                    .user(userInfo)
                    .build();

            log.info("User registered successfully: {}", registerRequest.getEmail());
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            log.error("Registration failed for user: {}", registerRequest.getEmail(), e);
            throw new BusinessException("Kayıt işlemi sırasında bir hata oluştu: " + e.getMessage());
        }
    }

    @PostMapping("/refresh")
    @Operation(summary = "Token yenileme", description = "Refresh token ile yeni access token alır")
    public ResponseEntity<JwtResponseDto> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        log.info("Token refresh attempt");

        try {
            // Bearer prefix'ini kaldır
            String token = refreshToken.startsWith("Bearer ") ? 
                    refreshToken.substring(7) : refreshToken;

            if (!jwtUtils.validateToken(token)) {
                throw new BusinessException("Geçersiz refresh token");
            }

            String email = jwtUtils.extractUsername(token);
            Vatandas vatandas = vatandasRepository.findByEmail(email)
                    .orElseThrow(() -> new BusinessException("Kullanıcı bulunamadı"));

            String newAccessToken = jwtUtils.generateAccessToken(vatandas);
            String newRefreshToken = jwtUtils.generateRefreshToken(vatandas);

            JwtResponseDto.UserInfoDto userInfo = JwtResponseDto.UserInfoDto.builder()
                    .id(vatandas.getId())
                    .ad(vatandas.getAd())
                    .soyad(vatandas.getSoyad())
                    .email(vatandas.getEmail())
                    .role(vatandas.getRole())
                    .sonGirisTarihi(LocalDateTime.now())
                    .build();

            JwtResponseDto response = JwtResponseDto.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .tokenType("Bearer")
                    .expiresAt(LocalDateTime.now().plusDays(1))
                    .user(userInfo)
                    .build();

            log.info("Token refreshed successfully for user: {}", email);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Token refresh failed", e);
            throw new BusinessException("Token yenileme başarısız: " + e.getMessage());
        }
    }

    @GetMapping("/me")
    @Operation(summary = "Kullanıcı bilgileri", description = "Giriş yapmış kullanıcının bilgilerini döner")
    public ResponseEntity<JwtResponseDto.UserInfoDto> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Vatandas vatandas = (Vatandas) authentication.getPrincipal();

        JwtResponseDto.UserInfoDto userInfo = JwtResponseDto.UserInfoDto.builder()
                .id(vatandas.getId())
                .ad(vatandas.getAd())
                .soyad(vatandas.getSoyad())
                .email(vatandas.getEmail())
                .role(vatandas.getRole())
                .sonGirisTarihi(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(userInfo);
    }
} 