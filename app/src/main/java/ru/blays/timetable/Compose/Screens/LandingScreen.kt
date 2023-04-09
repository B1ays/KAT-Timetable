package ru.blays.timetable.Compose.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LandingScreen() {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                TextButton(
                    onClick = { /*TODO*/ },
                ) {
                    Text(text = "Пропустить")
                }
            }

            Spacer(modifier = Modifier.height(80.dp))

            Box(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.5F)
                    .background(color = MaterialTheme.colorScheme.surfaceTint)
            )

            Spacer(modifier = Modifier.height(80.dp))

            Box(
                modifier = Modifier
                    .padding(36.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.4F),
                    onClick = { /*TODO*/ }) {
                    Text(
                        modifier = Modifier
                            .padding(12.dp),
                        text = "Далее")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}