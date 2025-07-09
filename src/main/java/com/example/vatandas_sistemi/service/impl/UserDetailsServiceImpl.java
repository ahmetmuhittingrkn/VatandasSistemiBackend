package com.example.vatandas_sistemi.service.impl;

import com.example.vatandas_sistemi.entity.Vatandas;
import com.example.vatandas_sistemi.repository.VatandasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final VatandasRepository vatandasRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("Loading user by email: {}", email);
        
        Vatandas vatandas = vatandasRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + email));
        
        if (!vatandas.getAktif()) {
            throw new UsernameNotFoundException("Kullanıcı aktif değil: " + email);
        }
        
        log.debug("User loaded successfully: {}", email);
        return vatandas;
    }
} 