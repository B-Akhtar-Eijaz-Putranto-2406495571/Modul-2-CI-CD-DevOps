<details>
<Summary><b>Refleksi 1</b></Summary>

### Clean code yang sudah diterapkan:
1. Meaningful Names: Memastikan semua variabel dan method sudah jelas nama dan fungsinya untuk apa
2. Function Size: Memastikan setiap fungsi mengerjakan 1 hal saja
3. DRY (Don't Repeat Yourself): Memastikan tidak ada logika yang berulang, kalau berulang dipindahkan ke bagian service

### Secure coding practices yang diterapkan:
1. Penggunaan UUID: Menggunakan UUID sebagai tipe data productId
2. Validation Ready: Menggunakan `````@ModelAttribute````` yang mempermudah jika ingin menambahkan anotasi validasi
</details>

<details>
<summary><b>Refleksi 2</b></summary>

1. Dengan adanya unit test, kode yang kita kerjakan bisa memastikan bahwa logika-logika yang terdapat dalam program berjalan sesuai ekspektasi. Selain itu, unit test juga dapat memudahkan proses debugging. Jika terjadi kesalahan di masa depan setelah mengganti kode, maka di dalam test akan langsung tahu bagian mana yang error.
Banyaknya unit test yang diperlukan untuk setiap projek biasanya 1 test untuk 1 function. Cara untuk memastikan unit test sudah cukup adalah ketika semua bagian kode sudah di test. Code coverage 100% artinya setiap bagian kode sudah dieksekusi tetapi bisa saja ada kemungkinan error karena kesalahan logika dalam kode
2. Jika saya membuat kelas test baru dengan menyalin prosedur setup dan variabel instance yang sama persis dari kelas sebelumnya, maka akan ada duplikasi kode yang melanggar principle clean code. Saran perbaikan
yang bisa dilakukan salah satunya adalah inheritance, yaitu membuat sebuah parent class (misalnya BaseFunctionalTest.java) yang menampung semua variabel umum seperti serverPort, testBaseUrl, dan metode setupTest. Kelas test lainnya cukup melakukan extends ke kelas ini
</details>


<details>
<Summary><b>Refleksi 3</b></Summary>

1. Code Quality Issues
    - Field Injection (menggunakan ```@Autowired``` pada field)
        - Issue: SonarCloud menandai penggunaan ```@Autowired``` langsung pada field (seperti ```private ProductService service;```) sebagai praktik yang kurang baik. Hal ini karena field injection membuat unit testing menjadi sulit, menyembunyikan dependensi kelas, dan membuat field tidak bisa bersifat immutable.
        - Strategi Perbaikan: Saya melakukan refactoring menjadi Constructor Injection. Lalu saya menghapus anotasi ```@Autowired``` dari field dan mengubah modifier field menjadi private final untuk menjamin immutability. Terakhir, saya membuat constructor yang menerima dependensi tersebut sebagai parameter. Spring secara otomatis akan menyuntikkan dependensi melalui constructor ini tanpa perlu anotasi ```@Autowired```.

    - Code Coverage yang Rendah (Uncovered Code)
        - Issue: SonarCloud melaporkan 0% coverage pada baris constructor baru karena laporan JaCoCo XML yang belum tergenerate.
        - Strategi Perbaikan: Pada konfigurasi CI, saya menambahkan plugin jacoco di build.gradle.kts, mengaktifkan laporan XML, dan memastikan workflow GitHub Actions menjalankan task jacocoTestReport sebelum analisis SonarCloud.
2. Menurut saya, implementasi saya saat ini sudah memenuhi definisi Continuous Integration (CI) dan Continuous Deployment (CD).

    - Untuk Continuous Integration (CI): Workflow GitHub Actions saya telah dikonfigurasi agar berjalan secara otomatis pada setiap push dan pull request ke dalam repositori. Pipeline CI ini secara konsisten menyiapkan Java environment, mengompilasi kode, dan mengeksekusi seluruh unit test secara otomatis untuk memverifikasi fungsionalitas aplikasi. Selain itu, pipeline ini juga terintegrasi dengan SonarCloud menggunakan laporan XML dari JaCoCo untuk menganalisis kualitas kode, mendeteksi code smells, dan mengukur test coverage secara terus-menerus. Hal ini memastikan bahwa hanya kode yang berkualitas dan bugnya sedikit yang dapat dimerge ke branch utama.
    - Untuk Continuous Deployment (CD): Saya telah menerapkan mekanisme auto-deploy menggunakan layanan PaaS (Koyeb) yang terhubung langsung dengan repositori GitHub. Dengan konfigurasi pull based, setiap perubahan yang berhasil lolos tahap CI dan dimerge ke branch utama akan secara otomatis dideteksi oleh Koyeb, dibangun ulang menggunakan ```Dockerfile```, dan di-deploy ke lingkungan produksi tanpa intervensi manual, sehingga aplikasi selalu dalam kondisi terbaru.

</details>