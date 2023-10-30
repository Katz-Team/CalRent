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
import androidx.compose.ui.res.stringResource
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
import vn.com.gatrong.calculaterent.R
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
                    if (viewModel.isAllInfoValid()) {
                        CoroutineScope(Dispatchers.Main).launch {
                            viewModel.getBill().let {
                                viewModel.insertBill(bill = it) {
                                    Navigator.navigateTo(Screen.BillScreen(it,false))
                                }
                            }
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

                        val kgElectPre = viewModel.getKgElectPre().collectAsStateWithLifecycle().value
                        val kgElectPreMessageError = viewModel.getKgElectPreMessageError().collectAsStateWithLifecycle().value

                        OutlinedTextField(
                            modifier = Modifier.weight(1f),
                            isError = kgElectPre.isEmpty() || kgElectPreMessageError.isNotEmpty(),
                            label = { Text("Khối điện tháng trước") },
                            value = kgElectPre,
                            onValueChange = {
                                if (it.isDigitsOnly()) {
                                    viewModel.setKgElectPre(it)
                                }
                            },
                            trailingIcon = { Text("Kwh") },
                            supportingText = {
                                if (kgElectPre.isEmpty()) {
                                    Text(text = stringResource(id = R.string.do_not_empty))
                                } else {
                                    if (kgElectPreMessageError.isNotEmpty()) {
                                        Text(text = stringResource(id = kgElectPreMessageError.toInt()))
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal,
                                imeAction = ImeAction.Next
                            )
                        )

                        Spacer(modifier = Modifier.weight(0.1f))

                        val kgElectNow = viewModel.getKgElectNow().collectAsStateWithLifecycle().value
                        val kgElectNowMessageError = viewModel.getKgElectNowMessageError().collectAsStateWithLifecycle().value
                                
                        OutlinedTextField(
                            modifier = Modifier.weight(1f),
                            isError = kgElectNow.isEmpty() || kgElectNowMessageError.isNotEmpty(),
                            label = { Text("Khối điện tháng này") },
                            value = kgElectNow,
                            onValueChange = {
                                if (it.isDigitsOnly()) {
                                    viewModel.setKgElectNow(it)
                                }
                            },
                            trailingIcon = { Text("Kwh") },
                            supportingText = {
                                if (kgElectNow.isEmpty()) {
                                    Text(text = stringResource(id = R.string.do_not_empty))
                                } else {
                                    if (kgElectNowMessageError.isNotEmpty()) {
                                        Text(text = stringResource(id = kgElectNowMessageError.toInt()))
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal,
                                imeAction = ImeAction.Next
                            )
                        )

                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {

                        val kgWaterPre = viewModel.getKgWaterPre().collectAsStateWithLifecycle().value
                        val kgWaterPreMessageError = viewModel.getKgWaterPreMessageError().collectAsStateWithLifecycle().value
                        
                        OutlinedTextField(
                            modifier = Modifier.weight(1f,fill = false),
                            isError = kgWaterPre.isEmpty() || kgWaterPreMessageError.isNotEmpty(),
                            label = { Text("Khối nước tháng trước") },
                            value = kgWaterPre,
                            onValueChange = {
                                if (it.isDigitsOnly()) {
                                    viewModel.setKgWaterPre(it)
                                }
                            },
                            trailingIcon = { Text("Dm3") },
                            supportingText = {
                                if (kgWaterPre.isEmpty()) {
                                    Text(text = stringResource(id = R.string.do_not_empty))
                                } else {
                                    if (kgWaterPreMessageError.isNotEmpty()) {
                                        Text(text = stringResource(id = kgWaterPreMessageError.toInt()))
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal,
                                imeAction = ImeAction.Done
                            )
                        )

                        Spacer(modifier = Modifier.weight(0.1f))

                        val kgWaterNow = viewModel.getKgWaterNow().collectAsStateWithLifecycle().value
                        val kgWaterNowMessageError = viewModel.getKgWaterNowMessageError().collectAsStateWithLifecycle().value
                        
                        OutlinedTextField(
                            modifier = Modifier.weight(1f,fill = false),
                            isError = kgWaterNow.isEmpty() || kgWaterNowMessageError.isNotEmpty(),
                            label = { Text("Khối nước tháng này") },
                            value = kgWaterNow,
                            onValueChange = {
                                if (it.isDigitsOnly()) {
                                    viewModel.setKgWaterNow(it)
                                }
                            },
                            trailingIcon = { Text("Dm3") },
                            supportingText = {
                                if (kgWaterNow.isEmpty()) {
                                    Text(text = stringResource(id = R.string.do_not_empty))
                                } else {
                                    if (kgWaterNowMessageError.isNotEmpty()) {
                                        Text(text = stringResource(id = kgWaterNowMessageError.toInt()))
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal,
                                imeAction = ImeAction.Done
                            )
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "Giá phòng",
                        style = MaterialTheme.typography.titleMedium
                    )

                    val moneyRoom = viewModel.getMoneyRoom().collectAsStateWithLifecycle().value

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        isError = moneyRoom.isEmpty(),
                        label = { Text("Tiền phòng") },
                        value = moneyRoom,
                        onValueChange = {
                            if (it.isDigitsOnly()) {
                                viewModel.setMoneyRoom(it)
                            }
                        },
                        trailingIcon = { Text("VND/Tháng") },
                        supportingText = {
                            if (moneyRoom.isEmpty()) {
                                Text(stringResource(id = R.string.do_not_empty))
                            }
                         },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done
                        )
                    )

                    val priceElect = viewModel.getPriceElect().collectAsStateWithLifecycle().value

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        isError = priceElect.isEmpty(),
                        label = { Text("Giá điện") },
                        value = priceElect,
                        onValueChange = {
                            if (it.isDigitsOnly()) {
                                viewModel.setPriceElect(it)
                            }
                        },
                        trailingIcon = { Text("VND/Tháng") },
                        supportingText = {
                            if (priceElect.isEmpty()) {
                                Text(stringResource(id = R.string.do_not_empty))
                            }
                         },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done
                        )
                    )

                    val priceWater = viewModel.getPriceWater().collectAsStateWithLifecycle().value

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        isError = priceWater.isEmpty(),
                        label = { Text("Giá nước") },
                        value = priceWater,
                        onValueChange = {
                            if (it.isDigitsOnly()) {
                                viewModel.setPriceWater(it)
                            }
                        },
                        trailingIcon = { Text("VND/Tháng") },
                        supportingText = {
                            if (priceWater.isEmpty()) {
                                Text(stringResource(id = R.string.do_not_empty))
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done
                        )
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "Phụ phí",
                        style = MaterialTheme.typography.titleMedium
                    )

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