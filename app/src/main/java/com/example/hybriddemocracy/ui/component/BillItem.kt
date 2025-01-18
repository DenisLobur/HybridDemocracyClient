package com.example.hybriddemocracy.composables.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.hybriddemocracy.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@Composable
fun BillItem(text: String, modifier: Modifier = Modifier) {

    val innerText by remember { mutableStateOf(text) }
    val date by remember { mutableStateOf(rndDate()) }
    val isChecked by remember { mutableStateOf(rndCheck()) }

    Row(
        modifier = modifier
            .fillMaxWidth()
//            .padding(horizontal = 20.dp)
            .border(
                BorderStroke(1.dp, colorResource(id = R.color.purple_700)),
                RoundedCornerShape(4.dp)
            )
    ) {
        Column(modifier = modifier.align(Alignment.CenterVertically)) {
            Text(text = innerText, modifier = modifier)
            Text(text = date, modifier = modifier)
        }

        Spacer(modifier = Modifier.weight(1f))

        Checkbox(checked = isChecked, onCheckedChange = {

        })
    }
}

private fun rndCheck(): Boolean = Random.nextBoolean()
private fun rndNum(): Int = Random.nextInt(100000, 1000000)
private fun rndDate(): String {
    val startYear = 2020
    val endYear = LocalDateTime.now().year
    val randomYear = Random.nextInt(startYear, endYear + 1)
    val randomMonth = Random.nextInt(1, 13)
    val randomDay = Random.nextInt(1, 29) // Simplified to avoid dealing with different month lengths
    val randomHour = Random.nextInt(0, 24)
    val randomMinute = Random.nextInt(0, 60)

    val randomDateTime = LocalDateTime.of(randomYear, randomMonth, randomDay, randomHour, randomMinute)
    val formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy - HH:mm")
    return randomDateTime.format(formatter)
}