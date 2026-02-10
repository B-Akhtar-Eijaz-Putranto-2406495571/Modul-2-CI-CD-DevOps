### Clean code yang sudah diterapkan:
1. Meaningful Names: Memastikan semua variabel dan method sudah jelas nama dan fungsinya untuk apa
2. Function Size: Memastikan setiap fungsi mengerjakan 1 hal saja
3. DRY (Don't Repeat Yourself): Memastikan tidak ada logika yang berulang, kalau berulang dipindahkan ke bagian service

### Secure coding practices yang diterapkan:
1. Penggunaan UUID: Menggunakan UUID sebagai tipe data productId
2. Validation Ready: Menggunakan `````@ModelAttribute````` yang mempermudah jika ingin menambahkan anotasi validasi
