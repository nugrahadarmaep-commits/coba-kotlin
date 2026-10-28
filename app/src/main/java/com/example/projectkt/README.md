# Form Registrasi Anggota Rumah Dinas (Jetpack Compose)

Proyek aplikasi Android berbasis Jetpack Compose untuk memenuhi tugas individu praktikum mengenai State, Event, dan Form Validation. Proyek ini telah dimodifikasi dari contoh modul awal dengan mengubah tema, palet warna, serta menambahkan field dan aturan validasi baru.

## Fitur & Modifikasi Tugas
- **State Preservation:** Menggunakan `rememberSaveable` agar data ketikan (Nama, NIM, Email, persetujuan SOP) tidak hilang saat perangkat di-rotate (perubahan orientasi layar).
- **Validasi Reaktif & Event Handling:** Menggunakan pola *Unidirectional Data Flow*. Peringatan error (border merah & teks bantuan) hanya akan muncul secara reaktif setelah pengguna menekan tombol submit.
- **Field & Aturan Tambahan (NIM):** Penambahan input Nomor Induk Mahasiswa (NIM) dengan aturan validasi ketat (wajib berisi digit angka dan minimal panjang 10 digit).
- **Feedback Dinamis:** Banner notifikasi sukses (hijau) yang secara dinamis menampilkan Nama dan NIM pendaftar setelah seluruh form tervalidasi.

## Cara Menjalankan Aplikasi
1. Buka terminal/CMD dan clone repositori ini ke komputer lokal:  
   `git clone https://github.com/USERNAME_LU/NAMA_REPO.git`
2. Buka aplikasi **Android Studio**, pilih *Open*, lalu cari folder proyek yang baru saja di-clone.
3. Tunggu beberapa saat hingga proses *Sync Project with Gradle Files* selesai tanpa error.
4. Hubungkan perangkat HP fisik via kabel (pastikan *USB Debugging* dan *Install via USB* aktif di Opsi Pengembang) atau nyalakan Emulator bawaan (AVD).
5. Pada toolbar bagian atas Android Studio, pastikan konfigurasi *run* sudah di-set ke **`app`**.
6. Klik tombol **Run** (ikon segitiga hijau) atau tekan shortcut keyboard **`Shift + F10`**.
7. Aplikasi akan langsung ter-install dan terbuka secara otomatis di layar perangkat untuk diuji coba!