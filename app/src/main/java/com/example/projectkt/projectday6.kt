package com.example.projectkt

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun RegistrationScreen() {
    // ==========================================
    // 1. STATE MANAGEMENT
    // ==========================================
    var nama by rememberSaveable { mutableStateOf("") }
    // Field Tambahan: NIM
    var nim by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    // State sementara tanpa saveable (keamanan credential)
    var password by remember { mutableStateOf("") }
    var konfirmasiPassword by remember { mutableStateOf("") }

    var setuju by rememberSaveable { mutableStateOf(false) }
    var passwordTerlihat by rememberSaveable { mutableStateOf(false) }

    var sudahSubmit by remember { mutableStateOf(false) }
    var berhasil by remember { mutableStateOf(false) }

    // ==========================================
    // 2. VALIDATION LOGIC
    // ==========================================
    val errorNama = sudahSubmit && nama.trim().isEmpty()
    // Aturan tambahan: NIM tidak boleh kosong, minimal 10 digit angka
    val errorNim = sudahSubmit && (nim.trim().length < 10 || !nim.all { it.isDigit() })
    val errorEmail = sudahSubmit && !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
    val errorPassword = sudahSubmit && password.length < 8
    val errorKonfirmasi = sudahSubmit && konfirmasiPassword != password
    val errorPersetujuan = sudahSubmit && !setuju

    val formValid = nama.trim().isNotEmpty() &&
            (nim.trim().length >= 10 && nim.all { it.isDigit() }) &&
            Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() &&
            password.length >= 8 &&
            konfirmasiPassword == password &&
            setuju

    // Layout Form
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header Baru
        Text(
            text = "RUMAH DINAS",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.5.sp
        )
        Text(
            text = "REGISTRASI ANGGOTA RUMAH DINAS",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A)
        )
        Text(
            text = "Lengkapi data diri akademik Anda untuk bergabung ke tim pengembang.",
            color = Color(0xFF64748B),
            fontSize = 14.sp
        )

        // Card Container Form
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Field 1: Nama Lengkap
                OutlinedTextField(
                    value = nama,
                    onValueChange = {
                        nama = it
                        berhasil = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Nama Lengkap") },
                    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                    singleLine = true,
                    isError = errorNama,
                    supportingText = { if (errorNama) Text("Nama lengkap wajib diisi") }
                )

                // Field Tambahan 2: NIM (Nomor Induk Mahasiswa)
                OutlinedTextField(
                    value = nim,
                    onValueChange = {
                        // Hanya update state jika input berisi digit angka
                        if (it.all { char -> char.isDigit() }) {
                            nim = it
                            berhasil = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Nomor Induk Mahasiswa (NIM)") },
                    leadingIcon = { Icon(Icons.Filled.Badge, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    isError = errorNim,
                    supportingText = {
                        if (errorNim) {
                            Text("NIM wajib angka dan minimal 10 digit")
                        } else {
                            Text("Contoh: 221080200001")
                        }
                    }
                )

                // Field 3: Email Kampus / Pribadi
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        berhasil = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Email Mahasiswa") },
                    leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isError = errorEmail,
                    supportingText = { if (errorEmail) Text("Format email tidak valid") }
                )

                // Field 4: Kata Sandi Akun
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        berhasil = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Kata Sandi") },
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                    trailingIcon = {
                        IconButton(onClick = { passwordTerlihat = !passwordTerlihat }) {
                            Icon(
                                imageVector = if (passwordTerlihat) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = if (passwordTerlihat) "Sembunyikan sandi" else "Tampilkan sandi"
                            )
                        }
                    },
                    visualTransformation = if (passwordTerlihat) VisualTransformation.None else PasswordVisualTransformation(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    isError = errorPassword,
                    supportingText = { if (errorPassword) Text("Kata sandi minimal 8 karakter") }
                )

                // Field 5: Konfirmasi Kata Sandi
                OutlinedTextField(
                    value = konfirmasiPassword,
                    onValueChange = {
                        konfirmasiPassword = it
                        berhasil = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Konfirmasi Kata Sandi") },
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    isError = errorKonfirmasi,
                    supportingText = { if (errorKonfirmasi) Text("Konfirmasi sandi tidak sesuai") }
                )

                // Checkbox Ketentuan
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Checkbox(
                        checked = setuju,
                        onCheckedChange = {
                            setuju = it
                            berhasil = false
                        },
                        colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                    )
                    Text(
                        text = "Saya bersedia mematuhi SOP Laboratorium",
                        fontSize = 13.sp,
                        color = Color(0xFF334155)
                    )
                }
                if (errorPersetujuan) {
                    Text(
                        text = "Centang persetujuan wajib diisi sebelum lanjut",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }

                // Event Button Submit
                Button(
                    onClick = {
                        sudahSubmit = true
                        berhasil = formValid
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Kirim Pendaftaran", modifier = Modifier.padding(vertical = 4.dp))
                }

                // Feedback UI Validasi Berhasil
                if (berhasil) {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFCCFBF1))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF0F766E)
                        )
                        Column {
                            Text(
                                text = "Pendaftaran Diterima!",
                                color = Color(0xFF0F766E),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Halo ${nama.trim()} (NIM: $nim), berkas Anda valid & siap diproses.",
                                color = Color(0xFF134E4A),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    MaterialTheme {
        RegistrationScreen()
    }
}