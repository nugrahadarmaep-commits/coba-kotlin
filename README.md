main project => app/src/main/java/com/example/day4projectkt


# Tugas Pertemuan 5 - Pemrograman Mobile

**Pengembangan Layout dan Adaptive UI dengan Jetpack Compose**

- **Nama:** Darma Nugraha
- **Program Studi:** Informatika
- **Kampus:** Universitas Muhammadiyah Sidoarjo

---

## 1. Hasil Running Aplikasi (Screenshot)

Tampilan antarmuka berhasil dijalankan dan diuji langsung melalui emulator Android Studio untuk memvalidasi adaptasi layout pada ukuran layar yang berbeda.

### A. Tampilan Mode Ponsel (Compact Window)

> Layar vertikal (lebar < 600 dp). Menggunakan susunan satu kolom penuh tanpa bilah navigasi samping agar konten tetap proporsional dan mudah dibaca di layar kecil.

<img width="296" height="596" alt="image" src="https://github.com/user-attachments/assets/bcb490cd-b6d0-4cdb-9ca2-1ec9647fb132" />

### B. Tampilan Mode Tablet / Layar Lebar (Expanded Window)

> Layar landscape/lebar (lebar >= 840 dp). Tata letak secara otomatis beradaptasi dengan menampilkan bilah navigasi samping (sidebar) dan membagi kartu mata kuliah ke dalam beberapa kolom grid.

<img width="670" height="522" alt="image" src="https://github.com/user-attachments/assets/51c1bddc-cb34-481d-906d-5fc82c679ef7" />


---

## 2. Rincian & Alasan Perubahan Source Code

Berikut adalah rangkuman potongan kode yang dirombak dari source code awal pertemuan 5 beserta alasan teknis di balik perubahannya:

### A. Diferensiasi Identitas Visual & Tema Warna

**Kode:**

```kotlin
private val PrimaryNavy = Color(0xFF1B4685)
private val SoftBlueContainer = Color(0xFFD9E3FF)
private val SecondaryAccent = Color(0xFF0F7C8C)
private val SoftCyanContainer = Color(0xFFE0F7FA)
private val WarningAmber = Color(0xFFB76E00)
private val SoftAmberContainer = Color(0xFFFFEAD5)
private val NeutralBackground = Color(0xFFF1F4FA)
private val MainText = Color(0xFF1E293B)
private val SubtitleText = Color(0xFF64748B)

@Composable
private fun PortalAkademikTheme(content: @Composable () -> Unit) {
    val colors = lightColorScheme(
        primary = PrimaryNavy,
        onPrimary = Color.White,
        primaryContainer = SoftBlueContainer,
        onPrimaryContainer = MainText,
        secondary = SecondaryAccent,
        background = NeutralBackground,
        surface = Color.White,
        onSurface = MainText,
        onSurfaceVariant = SubtitleText,
        tertiary = WarningAmber
    )
    MaterialTheme(colorScheme = colors, content = content)
}
```

### B. Variais komponen layout

**Kode:**

**1. Kartu Ringkasan dibuat geser horizontal**

```kotlin
Row(
    modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState())
        .padding(horizontal = 20.dp),
    horizontalArrangement = Arrangement.spacedBy(12.dp)
) {
    metricList.forEach { metric ->
        MetricCard(metric)
    }
}
```

**2. Styling ikon pada CourseCard menggunakan Box & Clip**

```kotlin
Box(
    modifier = Modifier
        .size(44.dp)
        .clip(RoundedCornerShape(12.dp))
        .background(course.bg),
    contentAlignment = Alignment.Center
) {
    Icon(
        course.icon,
        contentDescription = null,
        tint = course.color,
        modifier = Modifier.size(22.dp)
    )
}
```

Saya ubah strukturnya dengan memanfaatkan Row yang digabung dengan horizontalScroll(rememberScrollState()). Tujuannya agar kartu metrik bisa digeser ke samping dan area layar utama lebih fokus menampilkan daftar mata kuliah. Komponen Box juga dimanfaatkan untuk mengatur dekorasi background ikon kartu dengan radius sudut melengkung.

### C. logika adaptive ui dan responsive ruang layar

```kotlin
BoxWithConstraints(
    modifier = Modifier.fillMaxSize().padding(innerPadding)
) {
    val currentWidth = maxWidth
    val widthCategory = when {
        currentWidth < 600.dp -> "Compact (Ponsel)"
        currentWidth < 840.dp -> "Medium (Tablet Portait)"
        else -> "Expanded (Layar Lebar)"
    }
    val showSideRail = currentWidth >= 840.dp

    Row(modifier = Modifier.fillMaxSize()) {
        if (showSideRail) {
            SidebarNav() // Hanya dirender saat layar >= 840 dp
        }
        MainContentArea(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            screenMode = widthCategory
        )
    }
}
```

**LazyVerticalGrid otomatis membagi kolom**

```kotlin
LazyVerticalGrid(
    columns = GridCells.Adaptive(minSize = 250.dp),
    modifier = modifier.fillMaxSize(),
    contentPadding = PaddingValues(bottom = 24.dp),
    horizontalArrangement = Arrangement.spacedBy(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
) {
    // Render item kartu perkuliahan
}
```
