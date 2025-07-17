# Öğrenci Ders Ekleme Sistemi

Bu proje, Java Spring Boot kullanılarak geliştirilmiş bir **backend uygulamasıdır**. Üniversite veya eğitim kurumlarında öğrencilerin ders seçimi ve öğretmenlerin ders atamaları gibi işlemleri kolaylaştırmayı amaçlamaktadır.

##  Özellikler

- Öğrenci, öğretmen ve ders yönetimi
- Ders seçme (student-course) ilişkisi
- Öğretmenlere ders atama
- PostgreSQL veritabanı kullanımı
- RESTful API mimarisi
- E-posta ile kayıt/başvuru onayı (opsiyonel)

## 🛠 Kullanılan Teknolojiler

- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven

## ⚙ Projeyi Çalıştırma

### 1. Veritabanı Hazırlığı

PostgreSQL veritabanında `school_system` adında bir veritabanı oluşturun.

### 2. `application.properties` Ayarları

Kaynak klasöründeki `src/main/resources/application.properties` dosyasında veritabanı ayarlarını güncelleyin:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/school_system
spring.datasource.username=postgres
spring.datasource.password=senin_sifren
spring.jpa.hibernate.ddl-auto=update
