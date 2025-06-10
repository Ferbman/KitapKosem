![{24CC3345-894B-461B-BD68-3D7C38E579E2}](https://github.com/user-attachments/assets/acd5bb25-bf2f-4e37-ab0a-7cfa45d8e97f)# 📖 KitapKöşem - Online Kitap İnceleme ve Puanlama Sistemi

Bu proje, kullanıcıların kitapları listeleyebileceği, arayabileceği, detaylarını inceleyebileceği, yeni kitaplar ekleyebileceği, yorum yapabileceği ve 1-5 arası puan verebileceği dinamik bir web uygulamasıdır. Proje, modern Java web teknolojileri ve MVC (Model-View-Controller) mimarisi prensipleri kullanılarak geliştirilmiştir.

## ✨ Temel Özellikler

- **Kullanıcı Yönetimi:** Güvenli kullanıcı kaydı, girişi ve çıkışı.
- **Dinamik Navbar:** Kullanıcının giriş durumuna göre değişen interaktif menü çubuğu.
- **Kitap Listeleme ve Arama:** Veritabanındaki tüm kitapları modern bir kart tasarımıyla listeleme ve kitap adı/yazar adına göre anlık arama yapabilme.
- **Kitap Ekleme:** Giriş yapmış kullanıcıların kapak resmiyle birlikte sisteme yeni kitap ekleyebilmesi.
- **Resim Önizleme:** Kitap ekleme formunda, seçilen kapak resminin anlık olarak önizlemesinin gösterilmesi.
- **Kitap Detay Sayfası:** Her kitaba özel, kitap bilgilerini, puan ortalamasını ve yapılmış tüm yorumları gösteren detay sayfası.
- **Yorum Yapma:** Giriş yapmış kullanıcıların kitaplara yorum ekleyebilmesi.
- **Puanlama Sistemi:** Kullanıcıların kitaplara 1-5 arası yıldız vererek puan verebilmesi ve mevcut puanını güncelleyebilmesi.
- **Veri Silme:** Kullanıcıların kendi ekledikleri yorumları ve kitapları silebilmesi.
- **Kişisel Sayfa:** Kullanıcıların kendi ekledikleri kitapları gördüğü "Kitaplarım" sayfası.
- **Modern Arayüz:** Tüm sayfalarda tutarlı, koyu temalı ve şık bir kullanıcı arayüzü.

## 📸 Ekran Görüntüleri

*(Bu kısımlara projenizden aldığınız ekran görüntülerinin linklerini veya dosyalarını ekleyebilirsiniz.)*

**Giriş ve Kayıt Sayfaları**
![Giriş ve Kayıt Sayfası Tasarımı](https://imgur.com/a/REe9dbh)

**Ana Sayfa (Kitap Listesi)**
![Ana Sayfa Tasarımı](https://imgur.com/xJrZgCz)

**Kitap Detay Sayfası**
![Detay Sayfası Tasarımı](https://imgur.com/yMsJdyJ)

**Kitap Ekleme Sayfası**
![Kitap Ekleme Tasarımı](https://imgur.com/M50oAhM)


## 🛠️ Kullanılan Teknolojiler

### Frontend
- HTML5
- CSS3 (Flexbox, Grid)
- JavaScript (ES6)
- JSP (Jakarta Server Pages)
- JSTL (Jakarta Standard Tag Library)

### Backend
- Java 11+
- Jakarta EE 10 (Servlet 6.0, JSP 3.1)
- JDBC (Java Database Connectivity)
- MVC (Model-View-Controller) Mimarisi

### Veritabanı
- MySQL

### Sunucu ve Build Araçları
- Apache Tomcat (v10.1 veya v11.0)
- Apache Maven (Bağımlılık Yönetimi)
- **IDE:** IntelliJ IDEA Ultimate

## 🚀 Kurulum ve Çalıştırma

Bu projeyi yerel makinenizde çalıştırmak için aşağıdaki adımları izleyin.

### Gereksinimler
- JDK 11 veya daha yeni bir sürüm
- Apache Maven
- MySQL Veritabanı Sunucusu
- Apache Tomcat 10.1.x veya 11.x

### Adım Adım Kurulum

1.  **Projeyi Klonlama:**
    ```bash
    git clone [PROJE_GITHUB_LINKI]
    cd KitapKosem
    ```

2.  **Veritabanını Oluşturma:**
    MySQL'e bağlanın ve aşağıdaki SQL komutlarıyla veritabanını ve tabloları oluşturun:

    ```sql
    -- Önce veritabanını oluşturun
    CREATE DATABASE KitapKosemDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

    -- Oluşturduğunuz veritabanını kullanın
    USE KitapKosemDB;

    -- Tabloları oluşturun
    CREATE TABLE Users (
        user_id INT AUTO_INCREMENT PRIMARY KEY,
        username VARCHAR(100) NOT NULL UNIQUE,
        password_hash VARCHAR(255) NOT NULL,
        email VARCHAR(150) NOT NULL UNIQUE,
        registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

    CREATE TABLE Books (
        book_id INT AUTO_INCREMENT PRIMARY KEY,
        title VARCHAR(255) NOT NULL,
        author_name VARCHAR(150) NOT NULL,
        description TEXT,
        image_path VARCHAR(255) NULL,
        added_by_user_id INT,
        creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (added_by_user_id) REFERENCES Users(user_id) ON DELETE SET NULL
    );

    CREATE TABLE Comments (
        comment_id INT AUTO_INCREMENT PRIMARY KEY,
        book_id INT NOT NULL,
        user_id INT NOT NULL,
        comment_text TEXT NOT NULL,
        comment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (book_id) REFERENCES Books(book_id) ON DELETE CASCADE,
        FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
    );

    CREATE TABLE Ratings (
        rating_id INT AUTO_INCREMENT PRIMARY KEY,
        book_id INT NOT NULL,
        user_id INT NOT NULL,
        rating_value TINYINT NOT NULL CHECK (rating_value >= 1 AND rating_value <= 5),
        rating_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (book_id) REFERENCES Books(book_id) ON DELETE CASCADE,
        FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
        UNIQUE KEY user_book_rating (user_id, book_id)
    );
    ```

3.  **Veritabanı Bağlantısını Ayarlama:**
    Proje içindeki `src/main/java/com/kitapkosem/util/DatabaseUtil.java` dosyasını açın. `JDBC_USERNAME` ve `JDBC_PASSWORD` alanlarını kendi MySQL kullanıcı adı ve şifrenizle güncelleyin.

4.  **Projeyi Derleme:**
    Projenin kök dizininde terminali açın ve Maven ile projeyi derleyin:
    ```bash
    mvn clean package
    ```
    Bu komut, `target` klasörü altında `KitapKosem.war` adında bir dosya oluşturacaktır.

5.  **Tomcat'e Dağıtma (Deploy):**
    * Oluşturulan `KitapKosem.war` dosyasını, Tomcat'in kurulu olduğu dizindeki `webapps` klasörüne kopyalayın.
    * Tomcat'i başlatın. Tomcat, uygulamayı otomatik olarak dağıtacaktır.
    * Alternatif olarak, IntelliJ IDEA üzerinden Tomcat sunucu yapılandırması yaparak projeyi doğrudan çalıştırabilirsiniz.

6.  **Uygulamaya Erişme:**
    Tarayıcınızı açın ve `http://localhost:8080/KitapKosem/` adresine gidin. (Eğer Tomcat portunuz farklıysa `8080`'i değiştirin).

