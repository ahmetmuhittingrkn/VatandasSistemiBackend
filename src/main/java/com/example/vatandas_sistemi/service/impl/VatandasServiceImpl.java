package com.example.vatandas_sistemi.service.impl;

import com.example.vatandas_sistemi.dto.VatandasCreateDto;
import com.example.vatandas_sistemi.dto.VatandasDto;
import com.example.vatandas_sistemi.entity.Vatandas;
import com.example.vatandas_sistemi.exception.BusinessException;
import com.example.vatandas_sistemi.exception.ResourceNotFoundException;
import com.example.vatandas_sistemi.mapper.VatandasMapper;
import com.example.vatandas_sistemi.repository.VatandasRepository;
import com.example.vatandas_sistemi.service.VatandasService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class VatandasServiceImpl implements VatandasService {
    
    private final VatandasRepository vatandasRepository;
    private final VatandasMapper vatandasMapper;
    
    @Override
    public VatandasDto save(VatandasCreateDto createDto) {
        log.info("Yeni vatandaş oluşturuluyor: {}", createDto.getEmail());
        
        // Email kontrolü
        if (vatandasRepository.existsByEmail(createDto.getEmail())) {
            throw new BusinessException("Bu email adresi zaten kullanımda: " + createDto.getEmail());
        }
        
        // TC Kimlik No kontrolü
        if (createDto.getTcKimlikNo() != null && vatandasRepository.existsByTcKimlikNo(createDto.getTcKimlikNo())) {
            throw new BusinessException("Bu TC Kimlik No zaten kullanımda: " + createDto.getTcKimlikNo());
        }
        
        Vatandas vatandas = vatandasMapper.toEntity(createDto);
        
        Vatandas savedVatandas = vatandasRepository.save(vatandas);
        log.info("Vatandaş başarıyla oluşturuldu. ID: {}", savedVatandas.getId());
        
        return vatandasMapper.toDto(savedVatandas);
    }
    
    @Override
    public VatandasDto update(Long id, VatandasCreateDto updateDto) {
        log.info("Vatandaş güncelleniyor. ID: {}", id);
        
        Vatandas vatandas = vatandasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vatandaş", id));
        
        // Email kontrolü (kendisi hariç)
        if (!vatandas.getEmail().equals(updateDto.getEmail()) && 
            vatandasRepository.existsByEmail(updateDto.getEmail())) {
            throw new BusinessException("Bu email adresi zaten kullanımda: " + updateDto.getEmail());
        }
        
        // TC Kimlik No kontrolü (kendisi hariç)
        if (updateDto.getTcKimlikNo() != null && 
            !updateDto.getTcKimlikNo().equals(vatandas.getTcKimlikNo()) &&
            vatandasRepository.existsByTcKimlikNo(updateDto.getTcKimlikNo())) {
            throw new BusinessException("Bu TC Kimlik No zaten kullanımda: " + updateDto.getTcKimlikNo());
        }
        
        vatandasMapper.updateEntityFromDto(updateDto, vatandas);
        
        Vatandas updatedVatandas = vatandasRepository.save(vatandas);
        log.info("Vatandaş başarıyla güncellendi. ID: {}", updatedVatandas.getId());
        
        return vatandasMapper.toDto(updatedVatandas);
    }
    
    @Override
    @Transactional(readOnly = true)
    public VatandasDto findById(Long id) {
        Vatandas vatandas = vatandasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vatandaş", id));
        return vatandasMapper.toDto(vatandas);
    }
    
    @Override
    @Transactional(readOnly = true)
    public VatandasDto findByEmail(String email) {
        Vatandas vatandas = vatandasRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Vatandaş bulunamadı: " + email));
        return vatandasMapper.toDto(vatandas);
    }
    
    @Override
    @Transactional(readOnly = true)
    public VatandasDto findByTcKimlikNo(String tcKimlikNo) {
        Vatandas vatandas = vatandasRepository.findByTcKimlikNo(tcKimlikNo)
                .orElseThrow(() -> new ResourceNotFoundException("Vatandaş bulunamadı: " + tcKimlikNo));
        return vatandasMapper.toDto(vatandas);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<VatandasDto> findAll() {
        return vatandasRepository.findAll().stream()
                .map(vatandasMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<VatandasDto> findAll(Pageable pageable) {
        return vatandasRepository.findAll(pageable)
                .map(vatandasMapper::toDto);
    }
    
    @Override
    public void deleteById(Long id) {
        log.info("Vatandaş siliniyor. ID: {}", id);
        
        Vatandas vatandas = vatandasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vatandaş", id));
        
        // Soft delete
        vatandas.setAktif(false);
        vatandasRepository.save(vatandas);
        
        log.info("Vatandaş başarıyla silindi (soft delete). ID: {}", id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return vatandasRepository.existsByEmail(email);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByTcKimlikNo(String tcKimlikNo) {
        return vatandasRepository.existsByTcKimlikNo(tcKimlikNo);
    }
    

} 