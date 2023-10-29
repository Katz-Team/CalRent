package vn.com.gatrong.calculaterent.view.calScreen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        viewModel.getLastBillTemp().let {
                            Navigator.navigateTo(Screen.BillScreen(it,false))
                        }
                    }
                },
                icon = { Icon(Icons.Default.Check,Icons.Default.Check.name) },
                text = { Text("Tính tiền") },
            )
        },
        content = { innerPadding ->
            Box(modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = 14.dp,
                    end = 14.dp
                )) {

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(12.dp) ,
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {

                        OutlinedTextField(
                            modifier = Modifier.weight(1f),
                            label = { Text("Khối điện tháng trước") },
                            value = viewModel.getKgElectPre().collectAsStateWithLifecycle().value,
                            onValueChange = {
                                if (it.isDigitsOnly()) {
                                    viewModel.setKgElectPre(it)
                                }
                            },
                            trailingIcon = { Text("Kwh") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal,
                                imeAction = ImeAction.Next
                            )
                        )

                        Spacer(modifier = Modifier.weight(0.1f))

                        OutlinedTextField(
                            modifier = Modifier.weight(1f),
                            label = { Text("Khối điện tháng này") },
                            value = viewModel.getKgElectNow().collectAsStateWithLifecycle().value,
                            onValueChange = {
                                if (it.isDigitsOnly()) {
                                    viewModel.setKgElectNow(it)
                                }
                            },
                            trailingIcon = { Text("Kwh") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal,
                                imeAction = ImeAction.Next
                            )
                        )

                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {


                        OutlinedTextField(
                            modifier = Modifier.weight(1f,fill = false),
                            label = { Text("Khối nước tháng trước") },
                            value = viewModel.getKgWaterPre().collectAsStateWithLifecycle().value,
                            onValueChange = {
                                if (it.isDigitsOnly()) {
                                    viewModel.setKgWaterPre(it)
                                }
                            },
                            trailingIcon = { Text("Dm3") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal,
                                imeAction = ImeAction.Done
                            )
                        )

                        Spacer(modifier = Modifier.weight(0.1f))

                        OutlinedTextField(
                            modifier = Modifier.weight(1f,fill = false),
                            label = { Text("Khối nước tháng này") },
                            value = viewModel.getKgWaterNow().collectAsStateWithLifecycle().value,
                            onValueChange = {
                                if (it.isDigitsOnly()) {
                                    viewModel.setKgWaterNow(it)
                                }
                            },
                            trailingIcon = { Text("Dm3") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal,
                                imeAction = ImeAction.Done
                            )
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Text(text = "Giá phòng")

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Tiền phòng") },
                        value = viewModel.getMoneyRoom().collectAsStateWithLifecycle().value,
                        onValueChange = {
                            if (it.isDigitsOnly()) {
                                viewModel.setMoneyRoom(it)
                            }
                        },
                        trailingIcon = { Text("VND/Tháng") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done
                        )
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Giá điện") },
                        value = viewModel.getPriceElect().collectAsStateWithLifecycle().value,
                        onValueChange = {
                            if (it.isDigitsOnly()) {
                                viewModel.setPriceElect(it)
                            }
                        },
                        trailingIcon = { Text("VND/Tháng") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done
                        )
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Giá nước") },
                        value = viewModel.getPriceWater().collectAsStateWithLifecycle().value,
                        onValueChange = {
                            if (it.isDigitsOnly()) {
                                viewModel.setPriceWater(it)
                            }
                        },
                        trailingIcon = { Text("VND/Tháng") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done
                        )
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(text = "Phụ phí")

                    val surcharges = viewModel.getSurcharges()

                    surcharges.forEachIndexed { index, s ->
                        val value = s.collectAsStateWithLifecycle().value
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Phụ phí ${index + 1}") },
                            value = value,
                            onValueChange = {
                                viewModel.setSurcharges(index,it)
                            },
                            trailingIcon = { Text("VND/Tháng") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal,
                                imeAction = ImeAction.Done
                            )
                        )
                    }
                }
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