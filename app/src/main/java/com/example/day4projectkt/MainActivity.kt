package com.example.day4projectkt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Palet warna resmi bertema kampus modern
private val PrimaryNavy = Color(0xFF1B4685)
private val SoftBlueContainer = Color(0xFFD9E3FF)
private val SecondaryAccent = Color(0xFF0F7C8C)
private val SoftCyanContainer = Color(0xFFE0F7FA)
private val WarningAmber = Color(0xFFB76E00)
private val SoftAmberContainer = Color(0xFFFFEAD5)
private val NeutralBackground = Color(0xFFF4F6F9)
private val MainText = Color(0xFF1E293B)
private val SubtitleText = Color(0xFF64748B)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PortalAkademikTheme {
                AdaptiveDashboard()
            }
        }
    }
}

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AdaptiveDashboard() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = PrimaryNavy,
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Filled.AccountBalance,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Portal Akademik Mahasiswa",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MainText
                            )
                            Text(
                                text = "Universitas Muhammadiyah Sidoarjo",
                                style = MaterialTheme.typography.labelSmall,
                                color = SubtitleText
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = MainText
                )
            )
        },
        containerColor = NeutralBackground
    ) { innerPadding ->
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
                    SidebarNav()
                }
                MainContentArea(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    screenMode = widthCategory
                )
            }
        }
    }
}

@Composable
private fun SidebarNav() {
    Column(
        modifier = Modifier
            .width(92.dp)
            .fillMaxHeight()
            .background(Color.White)
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        SidebarItem(Icons.Filled.Dashboard, "Beranda", isSelected = true)
        SidebarItem(Icons.AutoMirrored.Filled.MenuBook, "KRS", isSelected = false)
        SidebarItem(Icons.Filled.Assignment, "Tugas", isSelected = false)
        SidebarItem(Icons.Filled.Person, "Profil", isSelected = false)
    }
}

@Composable
private fun SidebarItem(icon: ImageVector, title: String, isSelected: Boolean) {
    val bg = if (isSelected) SoftBlueContainer else Color.Transparent
    val tint = if (isSelected) PrimaryNavy else SubtitleText

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(bg),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = title, tint = tint)
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = tint,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun MainContentArea(modifier: Modifier, screenMode: String) {
    val metricList = listOf(
        MetricItem("SKS Terdaftar", "20 SKS", "Semester Ganjil", Icons.Filled.CheckCircle, PrimaryNavy, SoftBlueContainer),
        MetricItem("Tugas Aktif", "3 Tugas", "Menunggu Evaluasi", Icons.Filled.PendingActions, WarningAmber, SoftAmberContainer),
        MetricItem("IP Kumulatif", "3.82", "Status Sangat Memuaskan", Icons.Filled.School, SecondaryAccent, SoftCyanContainer)
    )

    val courseList = listOf(
        CourseItem("Pemrograman Mobile", "Jetpack Compose & Layout Adaptif", "Pertemuan 5 Selesai", 0.75f, Icons.Filled.Smartphone, PrimaryNavy, SoftBlueContainer),
        CourseItem("Jaringan Komputer", "Arsitektur Subnetting IPv4 & Routing", "Evaluasi Teori", 0.40f, Icons.Filled.Router, SecondaryAccent, SoftCyanContainer),
        CourseItem("Rekayasa Perangkat Lunak", "Dokumentasi Sprint & UML Use Case", "Pengerjaan Modul", 0.60f, Icons.Filled.Architecture, WarningAmber, SoftAmberContainer),
        CourseItem("Basis Data", "Normalisasi Tabel & Query Relasional", "Tugas Akhir", 0.90f, Icons.Filled.Storage, Color(0xFFB3261E), Color(0xFFF9DEDC))
    )

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 250.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            GreetingHeader(screenMode)
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Column(modifier = Modifier.padding(top = 4.dp)) {
                Text(
                    text = "Ringkasan Akademik",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MainText,
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                )
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
            }
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                text = "Daftar Mata Kuliah Semester 5",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MainText,
                modifier = Modifier.padding(start = 20.dp, top = 12.dp)
            )
        }

        items(courseList) { course ->
            Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                CourseCard(course)
            }
        }
    }
}

@Composable
private fun GreetingHeader(layoutMode: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PrimaryNavy)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Darma Nugraha",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Program Studi Informatika - Fakultas Sains dan Teknologi",
                style = MaterialTheme.typography.bodySmall,
                color = SoftBlueContainer
            )
            Spacer(Modifier.height(14.dp))
            Surface(
                color = Color.White.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Konfigurasi Layar: $layoutMode",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
    }
}

private data class MetricItem(
    val title: String,
    val value: String,
    val desc: String,
    val icon: ImageVector,
    val color: Color,
    val bg: Color
)

@Composable
private fun MetricCard(data: MetricItem) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.width(175.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier.size(40.dp).clip(CircleShape).background(data.bg),
                contentAlignment = Alignment.Center
            ) {
                Icon(data.icon, contentDescription = null, tint = data.color, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.height(14.dp))
            Text(data.value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MainText)
            Text(data.title, style = MaterialTheme.typography.labelSmall, color = SubtitleText)
        }
    }
}

private data class CourseItem(
    val name: String,
    val detail: String,
    val status: String,
    val progress: Float,
    val icon: ImageVector,
    val color: Color,
    val bg: Color
)

@Composable
private fun CourseCard(course: CourseItem) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(course.bg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(course.icon, contentDescription = null, tint = course.color, modifier = Modifier.size(22.dp))
                }
                Spacer(Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(course.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MainText, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(course.detail, style = MaterialTheme.typography.bodySmall, color = SubtitleText)
                }
            }
            Spacer(Modifier.height(18.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(course.status, style = MaterialTheme.typography.labelSmall, color = course.color, fontWeight = FontWeight.Bold)
                Text("${(course.progress * 100).toInt()}%", style = MaterialTheme.typography.labelSmall, color = MainText, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { course.progress },
                modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape),
                color = course.color,
                trackColor = NeutralBackground
            )
        }
    }
}

@Preview(name = "Mode HP", showBackground = true, widthDp = 400, heightDp = 800)
@Composable
fun MobilePreview() {
    PortalAkademikTheme { AdaptiveDashboard() }
}

@Preview(name = "Mode Tablet", showBackground = true, widthDp = 900, heightDp = 700)
@Composable
fun TabletPreview() {
    PortalAkademikTheme { AdaptiveDashboard() }
}