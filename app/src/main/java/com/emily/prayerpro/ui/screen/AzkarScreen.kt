package com.emily.prayerpro.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AzkarScreen(
    category: String,
    onBack: () -> Unit
) {
    // This will load from the azkar.json asset in the real app
    val azkarList = remember { mutableStateListOf<String>() }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("الأذكار") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("←")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(azkarList) { zekr ->
                AzkarItem(zekr = zekr)
            }
        }
    }
}

@Composable
fun AzkarItem(zekr: String) {
    var count by remember { mutableStateOf(0) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        onClick = { count++ }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = zekr, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp)):
            Text(
                text = "$count",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}