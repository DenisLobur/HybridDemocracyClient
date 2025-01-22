package com.example.hybriddemocracy.ui.screens.detail

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.CircularProgressIndicator
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

@Composable
fun Detail(navController: NavController, billId: Long, citizenId: Long, modifier: Modifier = Modifier) {
    val viewModel: HomeViewModel = hiltViewModel<HomeViewModel>()

    var rating by remember { mutableIntStateOf(0) }
    var feedback by remember { mutableStateOf("") }
    var isInterpreted by remember { mutableStateOf(false) }
    var nreg by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var billy: Bill? by remember { mutableStateOf(null) }
    var longText by remember { mutableStateOf("") }
    var interpretedText by remember { mutableStateOf("") }

    LaunchedEffect(billId, citizenId) {
        viewModel.getBillById(billId, citizenId) { bill ->
            billy = bill
            nreg = bill.nreg
            title = bill.title
            rating = bill.rating
            feedback = bill.feedback
        }
    }

    LaunchedEffect(billy) {
        billy?.let {
            viewModel.getBillTextByNreg(it.nreg) { text ->
                longText = text
            }
        }
    }

    Column {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxHeight()
        ) {
            Text(
                text = nreg.uppercase(),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(id = R.color.purple_700)
                ),
                modifier = modifier
                    .padding(top = 20.dp)
                    .padding(horizontal = 20.dp)
            )
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
                    .padding(top = 10.dp)
                    .padding(horizontal = 20.dp)
            )

            OutlinedTextField(
                value = if (longText.isEmpty()) "Loading bill..." else longText,
                onValueChange = { newText ->

                },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 20.dp, end = 20.dp)
                    .heightIn(max = 280.dp)
                    .border(
                        BorderStroke(1.dp, colorResource(id = R.color.purple_700)),
                        RoundedCornerShape(4.dp)
                    )
                    .verticalScroll(rememberScrollState())
            )

            Button(
                onClick = {
                    viewModel.summarizeText(longText) { shortText ->
                        Log.d("denys", "shortText: $shortText")
                        interpretedText = shortText
                        isInterpreted = true
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.purple_700)),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(text = "Interpret with AI".uppercase())
            }

            if (isInterpreted) {
                OutlinedTextField(
                    value = interpretedText,
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

            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
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
        }

        Column {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = {
                        viewModel.vote(billId, citizenId, rating, feedback) { isVoted ->
                            navController.popBackStack()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.purple_700)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Text(text = "Send feedback".uppercase())
                }
            }
        }
    }
}