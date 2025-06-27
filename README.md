# Mamak Belediyesi Vatandaş Şikayet-Öneri Sistemi

Bu proje, Mamak Belediyesi vatandaşlarının şikayet ve önerilerini dijital ortamda iletebilmeleri için geliştirilmiş bir backend sistemidir.

## 🚀 Özellikler

### 📋 Ana İşlevler
- **Vatandaş Kayıt Sistemi**: Vatandaşların sistem üzerinde kayıt olabilmesi
- **Şikayet/Öneri Yönetimi**: Kategorize edilmiş talep oluşturma ve takip etme
- **Takip Sistemi**: Benzersiz takip numarası ile durum sorgulama
- **Kategori Yönetimi**: Farklı belediye hizmet kategorileri
- **Durum Takibi**: Taleplerin anlık durumu ve süreç takibi
- **İstatistik Dashboard**: Yönetici paneli için detaylı raporlama

### 🔧 Teknik Özellikler
- **Spring Boot 3.x** - Modern Java framework
- **PostgreSQL** - Güvenilir veritabanı yönetimi
- **JPA/Hibernate** - ORM ve veritabanı işlemleri
- **Swagger/OpenAPI** - API dokümantasyonu
- **Global Exception Handling** - Merkezi hata yönetimi
- **DTO Pattern** - Katmanlı mimari
- **Service/Repository Pattern** - Temiz kod mimarisi
- **Soft Delete** - Veri güvenliği
- **Audit Fields** - Veri takibi

## 📱 Mobil Entegrasyon
Bu API, mobil uygulamalar için özel olarak tasarlanmıştır:
- RESTful API endpoints
- JSON response format
- Pagination desteği
- Search ve filtering
- File upload desteği

## 🛠️ Kurulum

### Gereksinimler
- Java 17 veya üzeri
- Maven 3.8+
- PostgreSQL 12+

### Adımlar

1. **Projeyi klonlayın**
```bash
git clone <repository-url>
cd vatandas-sistemi
```

2. **PostgreSQL veritabanı oluşturun**
```sql
CREATE DATABASE vatandas_sistemi_db;
```

3. **application.properties dosyasını güncelleyin**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/vatandas_sistemi_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

4. **Projeyi çalıştırın**
```bash
mvn spring-boot:run
```

## 📚 API Dokümantasyonu

Proje çalıştıktan sonra aşağıdaki adresten API dokümantasyonuna erişebilirsiniz:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs

## 🏗️ Mimari

### Katmanlar
```
├── Controller Layer    # REST API endpoints
├── Service Layer      # Business logic (Interface + Impl)
├── Repository Layer   # Database operations
├── Entity Layer       # JPA entities
├── DTO Layer         # Data transfer objects
├── Exception Layer   # Custom exception handling
└── Config Layer      # Configuration classes
```

### Ana Entity'ler
- **Vatandas**: Vatandaş bilgileri
- **Talep**: Şikayet/öneri talepleri
- **Kategori**: Talep kategorileri
- **TalepYorum**: Talep üzerindeki yorumlar
- **TalepEk**: Taleplere eklenen dosyalar

## 🔌 API Endpoints

### Vatandaş İşlemleri
- `POST /api/vatandas` - Yeni vatandaş kaydı
- `GET /api/vatandas/{id}` - Vatandaş detayı
- `PUT /api/vatandas/{id}` - Vatandaş güncelleme
- `GET /api/vatandas/email/{email}` - Email ile arama

### Talep İşlemleri
- `POST /api/talep` - Yeni talep oluşturma
- `GET /api/talep/{id}` - Talep detayı
- `GET /api/talep/takip/{takipNo}` - Takip numarası ile sorgulama
- `PUT /api/talep/{id}/durum` - Durum güncelleme
- `GET /api/talep/vatandas/{vatandasId}` - Vatandaş talepleri

### Kategori İşlemleri
- `GET /api/kategori/aktif` - Aktif kategoriler
- `GET /api/kategori/{id}` - Kategori detayı
- `POST /api/kategori` - Yeni kategori oluşturma

### İstatistik Endpoints
- `GET /api/talep/istatistik/durum` - Durum istatistikleri
- `GET /api/talep/istatistik/tip` - Tip istatistikleri

## 📊 Veritabanı Şeması

### Başlıca Tablolar
- `vatandas`: Vatandaş bilgileri
- `talep`: Ana talep tablosu
- `kategori`: Talep kategorileri
- `talep_yorum`: Talep yorumları
- `talep_ek`: Talep ekleri

### Önemli Alanlar
- **Takip Numarası**: Her talep için benzersiz (TAL + tarih + 4 haneli sayı)
- **Durum**: BEKLEMEDE, INCELENIYOR, COZULDU, REDDEDILDI, IPTAL
- **Tip**: SIKAYET, ONERI
- **Öncelik**: DUSUK, ORTA, YUKSEK, ACIL

## 🔐 Güvenlik

- CSRF koruması (development için kapalı)
- SQL Injection koruması (JPA/Hibernate)
- Input validation (Bean Validation)
- Soft delete (veri korunması)

## 🧪 Test

```bash
# Unit testleri çalıştırma
mvn test

# Proje derleme
mvn clean compile

# Packaging
mvn clean package
```

## 📝 Geliştirme Notları

### Gelecek Özellikler
- [ ] File upload işlevselliği
- [ ] Email bildirimleri
- [ ] Push notification
- [ ] Advanced search
- [ ] Dashboard analytics
- [ ] User authentication/authorization
- [ ] Rate limiting
- [ ] Caching

### Mobil App Entegrasyonu
Bu API şu mobile teknolojiler ile kullanılabilir:
- React Native
- Flutter
- Android (Java/Kotlin)
- iOS (Swift)

## 👥 Katkıda Bulunma

1. Fork yapın
2. Feature branch oluşturun (`git checkout -b feature/amazing-feature`)
3. Commit yapın (`git commit -m 'Add amazing feature'`)
4. Push edin (`git push origin feature/amazing-feature`)
5. Pull Request açın

## 📄 Lisans

Bu proje MIT lisansı altında lisanslanmıştır.

## 📞 İletişim

**Mamak Belediyesi IT Ekibi**
- Email: it@mamak.bel.tr
- Website: https://mamak.bel.tr

---

*Bu proje Mamak Belediyesi'nde staj kapsamında geliştirilmiştir.* 