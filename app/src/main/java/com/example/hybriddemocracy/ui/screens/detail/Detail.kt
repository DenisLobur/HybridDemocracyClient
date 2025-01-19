package com.example.hybriddemocracy.ui.screens.detail

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hybriddemocracy.R
import com.example.hybriddemocracy.data.model.Bill
import com.example.hybriddemocracy.ui.screens.home.HomeViewModel
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

@Composable
fun Detail(navController: NavController, billId: Long, modifier: Modifier = Modifier) {
    var rating by remember { mutableIntStateOf(0) }
    var feedback by remember { mutableStateOf("") }
    var isInterpreted by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var billy: Bill? by remember { mutableStateOf(null) }
    var mainText by remember { mutableStateOf("") }

    val viewModel: HomeViewModel = hiltViewModel<HomeViewModel>()

    LaunchedEffect(billId) {
        viewModel.getBillById(billId) { bill ->
            //Log.d("denys", "bill details: $bill")
            billy = bill
            title = bill.title
            rating = bill.rating
            feedback = bill.feedback
        }
    }

    LaunchedEffect(billy) {
        viewModel.getBillTextByNreg(billy?.nreg ?: "") { text ->
            mainText = text
            Log.d("denys", "mainText: $mainText")
        }
//        mainText = fetchHtml("https://data.rada.gov.ua/laws/show/n0019500-25.txt") ?: ""
//        Log.d("denys", "mainText: $mainText")
    }


    Column {
        Text(
            text = title.uppercase(),
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier
                .padding(top = 20.dp)
                .padding(horizontal = 20.dp)
        )

        OutlinedTextField(
            value = mainText,
            onValueChange = { newText ->

            },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 20.dp, end = 20.dp)
                .heightIn(max = 300.dp)
                .border(
                    BorderStroke(1.dp, colorResource(id = R.color.purple_700)),
                    RoundedCornerShape(4.dp)
                )
                .verticalScroll(rememberScrollState())
        )

        Button(
            onClick = {
                //TODO: Interpret with AI
                isInterpreted = true
//                viewModel.getUserById("user_id") { user ->
                    // Handle the user data
//                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.purple_700)),
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(text = "Interpret by AI".uppercase())
        }

        if (isInterpreted) {
            OutlinedTextField(
                value = "interpretedText",
                onValueChange = { newText ->

                },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 16.dp)
                    .heightIn(max = 100.dp)
                    .border(
                        BorderStroke(1.dp, colorResource(id = R.color.purple_700)),
                        RoundedCornerShape(4.dp)
                    )
                    .verticalScroll(rememberScrollState())
            )
        }



        Row(modifier = Modifier
            .padding(horizontal = 20.dp)
            .align(Alignment.CenterHorizontally)) {
            for (i in 1..5) {
                IconButton(onClick = { rating = i }) {
                    Icon(
                        imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = "Rating $i",
                        tint = if (i <= rating) Color.Yellow else Color.Blue
                    )
                }
            }
        }

        OutlinedTextField(
            value = feedback,
            onValueChange = { newText ->
                feedback = newText
            },
            placeholder = { Text(text = "Leave your feedback regarding this bill") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 16.dp)
                .border(
                    BorderStroke(1.dp, colorResource(id = R.color.purple_700)),
                    RoundedCornerShape(4.dp)
                )
        )

        Button(
            onClick = {
                //TODO: Send feedback to BE
                //navController.popBackStack()
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.purple_700)),
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(text = "Seend feedback".uppercase())
        }
    }
}

fun fetchHtml(url: String): String? {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url(url)
        .build()

    return try {
        val response: Response = client.newCall(request).execute()
        if (response.isSuccessful) {
            response.body?.string()
        } else {
            null
        }
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

//private val mainText: String = "Запроваджено оновлену інформаційну взаємодію між ЄДР та ДПС з використанням засобів системи «Трембіта»\n" +
//        "\n" +
//        "Відповідно до наказу Міністерства юстиції України та Міністерства фінансів України від 05.07.2024  № 2040/5/327 «Про електронну інформаційну взаємодію між Єдиним державним реєстром юридичних осіб, фізичних осіб - підприємців та громадських формувань й інформаційними системами Державної податкової служби України», зареєстрований в Міністерстві юстиції України 09.07.2024 № 1026/42371, 09.12.2024 запроваджено оновлену інформаційну взаємодію між Єдиним державним реєстром юридичних осіб, фізичних осіб - підприємців та громадських формувань й інформаційними системами Державної податкової служби України з використанням засобів системи електронної взаємодії державних електронних інформаційних ресурсів «Трембіта»."
//
//private val interpretedText = "- Оновлено інформаційну взаємодію між ЄДР та ДПС з використанням системи «Трембіта».\n" +
//        "- Відповідно до наказу Міністерства юстиції та Міністерства фінансів України від 05.07.2024, зареєстрованого 09.07.2024.\n" +
//        "- Впроваджено 09.12.2024 для покращення електронної взаємодії між інформаційними системами ЄДР та ДПС."