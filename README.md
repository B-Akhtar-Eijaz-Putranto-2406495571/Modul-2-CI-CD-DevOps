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