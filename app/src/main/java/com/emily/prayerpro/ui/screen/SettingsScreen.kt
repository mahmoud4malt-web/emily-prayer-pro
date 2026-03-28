package com.emily.prayerpro.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("الإعدادات") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("←")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("طريقة الحساب", style = MaterialTheme.typography.labelLarge)
            // Dropdown for calculation methods...
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(modifier = Modifier.fillMaxWidth()) {
                Text("تفعيل الأذان")
                Spacer(modifier = Modifier.weight(1))
                Switch(checked = true, onCheckedChange = {})
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(modifier = Modifier.fillMaxWidth()) {
                Text("تنبيه وضع الصامت")
                Spacer(modifier = Modifier.weight(1))
                Switch(checked = true, onCheckedChange = {})
            }
        }
    }
}