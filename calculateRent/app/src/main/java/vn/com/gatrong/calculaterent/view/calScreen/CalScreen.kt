package vn.com.gatrong.calculaterent.view.calScreen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vn.com.gatrong.calculaterent.navigation.Screen
import vn.com.gatrong.calculaterent.navigation.Navigator

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalScreen() {
    val viewModel = viewModel<CalViewModel>()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Tính tiền tháng 7", color = MaterialTheme.colorScheme.onSurfaceVariant)
                },
                navigationIcon = {
                    IconButton(onClick = { Navigator.back() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = Icons.Default.ArrowBack.name)
                    }
                }
            ) },
        content = { innerPadding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp, bottom = 60.dp)) {

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f),
                    verticalArrangement = Arrangement.spacedBy(14.dp) ,
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(ButtonDefaults.IconSize)
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        label = { Text("Khối Điện") },
                        value = viewModel.getElect().collectAsStateWithLifecycle().value,
                        onValueChange = {
                            if (it.isDigitsOnly()) {
                                viewModel.setElect(it)
                            }
                        },
                        trailingIcon = { Text("Kwh") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Next
                        )
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        label = { Text("Khối Nước") },
                        value = viewModel.getWater().collectAsStateWithLifecycle().value,
                        onValueChange = {
                            if (it.isDigitsOnly()) {
                                viewModel.setWater(it)
                            }
                        },
                        trailingIcon = { Text("Dm3") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done
                        )
                    )


                }

                Button(modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .align(Alignment.BottomCenter),
                    onClick = {
                        CoroutineScope(Dispatchers.Main).launch {
                            viewModel.getLastBillTemp().let {
                                Navigator.navigateTo(Screen.BillScreen(it,false))
                            }
                        }
                    }
                ) { Text("Tính tiền") }
            }
        }
    )


    BackHandler() {
        Navigator.back()
    }
}


@Preview
@Composable
fun PreviewCalScreen() {
    CalScreen()
}