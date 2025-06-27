package com.example.vatandas_sistemi.mapper;

import com.example.vatandas_sistemi.dto.*;
import com.example.vatandas_sistemi.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TalepMapper {
    
    private final VatandasMapper vatandasMapper;
    private final KategoriMapper kategoriMapper;
    
    public TalepDto toDto(Talep talep) {
        if (talep == null) {
            return null;
        }
        
        return TalepDto.builder()
                .id(talep.getId())
                .baslik(talep.getBaslik())
                .aciklama(talep.getAciklama())
                .tip(talep.getTip())
                .durum(talep.getDurum())
                .oncelik(talep.getOncelik())
                .takipNo(talep.getTakipNo())
                .konumLatitude(talep.getKonumLatitude())
                .konumLongitude(talep.getKonumLongitude())
                .konumAciklama(talep.getKonumAciklama())
                .vatandas(vatandasMapper.toDtoMinimal(talep.getVatandas()))
                .kategori(kategoriMapper.toDto(talep.getKategori()))
                .olusturmaTarihi(talep.getOlusturmaTarihi())
                .guncellemeTarihi(talep.getGuncellemeTarihi())
                .build();
    }
    
    public TalepDto toDtoWithDetails(Talep talep, List<TalepYorum> yorumlar, List<TalepEk> ekler) {
        if (talep == null) {
            return null;
        }
        
        TalepDto dto = toDto(talep);
        dto.setYorumlar(yorumlarToDto(yorumlar));
        dto.setEkler(eklerToDto(ekler));
        
        return dto;
    }
    
    public TalepYorumDto yorumToDto(TalepYorum yorum) {
        if (yorum == null) {
            return null;
        }
        
        return TalepYorumDto.builder()
                .id(yorum.getId())
                .yorum(yorum.getYorum())
                .yorumYapan(yorum.getYorumYapan())
                .yorumYapanTip(yorum.getYorumYapanTip())
                .gorunur(yorum.getGorunur())
                .olusturmaTarihi(yorum.getOlusturmaTarihi())
                .build();
    }
    
    public TalepEkDto ekToDto(TalepEk ek) {
        if (ek == null) {
            return null;
        }
        
        return TalepEkDto.builder()
                .id(ek.getId())
                .dosyaAdi(ek.getDosyaAdi())
                .dosyaYolu(ek.getDosyaYolu())
                .dosyaTipi(ek.getDosyaTipi())
                .dosyaBoyutu(ek.getDosyaBoyutu())
                .aciklama(ek.getAciklama())
                .olusturmaTarihi(ek.getOlusturmaTarihi())
                .build();
    }
    
    public List<TalepYorumDto> yorumlarToDto(List<TalepYorum> yorumlar) {
        if (yorumlar == null) {
            return null;
        }
        
        return yorumlar.stream()
                .map(this::yorumToDto)
                .collect(Collectors.toList());
    }
    
    public List<TalepEkDto> eklerToDto(List<TalepEk> ekler) {
        if (ekler == null) {
            return null;
        }
        
        return ekler.stream()
                .map(this::ekToDto)
                .collect(Collectors.toList());
    }
} 