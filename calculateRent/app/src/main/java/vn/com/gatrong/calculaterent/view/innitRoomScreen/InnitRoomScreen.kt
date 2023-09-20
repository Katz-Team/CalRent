package vn.com.gatrong.calculaterent.view.innitRoomScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import vn.com.gatrong.calculaterent.R
import vn.com.gatrong.calculaterent.extensions.formatToMoney
import vn.com.gatrong.calculaterent.extensions.isDigitsOrDot
import vn.com.gatrong.calculaterent.model.DefaultSurcharge

@Composable
fun InnitScreen() {
    val viewModel = viewModel<InnitRoomViewModel>()
    val step by viewModel.stateInnit.collectAsStateWithLifecycle()

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 60.dp, bottom = 60.dp)) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f),
            verticalArrangement = Arrangement.spacedBy(14.dp) ,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                stringResource(id = R.string.initRoom),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )


            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(ButtonDefaults.IconSize)
            )

            when(step.step) {
                StateInnit.STEP1 -> {
                    Step1()
                }
                StateInnit.STEP2 -> {
                    Step2()
                }
                StateInnit.STEP3 -> {
                    Step3()
                }
            }

        }

        Button(modifier = Modifier
            .fillMaxWidth(0.7f)
            .align(Alignment.BottomCenter),
            enabled = viewModel.enableButtonNext.value,
            onClick = {  viewModel.nextStep() }
        ) { Text("Tiếp tục") }

    }

    BackHandler {
        viewModel.preStep()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Step1() {
    val viewModel = viewModel<InnitRoomViewModel>()
    val rentHouse = viewModel.rentHouse.collectAsStateWithLifecycle().value
    var position = rememberSaveable {
        mutableStateOf(0)
    }
    OutlinedTextField(
        label = { Text("Ngày bắt đầu") },
        value = viewModel.time.collectAsStateWithLifecycle().value,
        onValueChange = {
            if (it.isDigitsOrDot()) {
                viewModel.time.value = it
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        )
    )

    OutlinedTextField(label = { Text("Tiền thuê phòng") },
        value = TextFieldValue(text = rentHouse.formatToMoney(),selection = TextRange(rentHouse.formatToMoney().length)),
        onValueChange = {
            if (it.text.isDigitsOrDot()) {
                viewModel.rentHouse.value = it.text.replace(".","")
            }
        },
        singleLine = true,
        trailingIcon = { Text("VND") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        )
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Step2() {
    val viewModel = viewModel<InnitRoomViewModel>()
    val rentElect = viewModel.rentElect.collectAsStateWithLifecycle().value
    val rentWater = viewModel.rentWater.collectAsStateWithLifecycle().value

    OutlinedTextField(label = { Text("Giá điện") },
        value = TextFieldValue(text = rentElect.formatToMoney(),selection = TextRange(rentElect.formatToMoney().length)),
        onValueChange = {
            if (it.text.isDigitsOrDot()) {
                viewModel.rentElect.value = it.text.replace(".","")
            }
        },
        trailingIcon = { Text("VND") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Next
        )
    )

    OutlinedTextField(label = { Text("Khối điện khởi điểm") },
        value = viewModel.kgElect.collectAsStateWithLifecycle().value,
        onValueChange = {
            if (it.isDigitsOnly()) {
                viewModel.kgElect.value = it
            }
        },
        trailingIcon = { Text("Kwh") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Next
        )
    )

    OutlinedTextField(label = { Text("Giá nước") },
        value = TextFieldValue(text = rentWater.formatToMoney(),selection = TextRange(rentWater.formatToMoney().length)),
        onValueChange = {
            if (it.text.isDigitsOrDot()) {
                viewModel.rentWater.value = it.text.replace(".","")
            }
        },
        trailingIcon = { Text("VND") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Next
        )
    )

    OutlinedTextField(label = { Text("Khối nước khởi điểm") },
        value = viewModel.kgWater.collectAsStateWithLifecycle().value,
        onValueChange = {
            if (it.isDigitsOnly()) {
                viewModel.kgWater.value = it
            }
        },
        trailingIcon = { Text("Dm3") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        )
    )
}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Step3() {
    val viewModel = viewModel<InnitRoomViewModel>()

    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)) {

        items(viewModel.defaultSurcharges.size) { index ->
            val defaultSurcharge = viewModel.defaultSurcharges.get(index).collectAsStateWithLifecycle().value

            OutlinedTextField(label = { Text("${defaultSurcharge.name} ${index + 1}") },
                value = TextFieldValue(text = defaultSurcharge.price.formatToMoney(),selection = TextRange(defaultSurcharge.price.formatToMoney().length)),
                onValueChange = {
                    if (it.text.isDigitsOrDot()) {
                        if (it.text.isNotEmpty()) {
                            viewModel.defaultSurcharges.get(index).value = DefaultSurcharge(price = it.text.replace(".","").toLong())
                        } else {
                            viewModel.defaultSurcharges.get(index).value = DefaultSurcharge(price = 0)
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                )
            )
        }

        item {
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(0.7f),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ,onClick = {
                    viewModel.defaultSurcharges.add(MutableStateFlow(DefaultSurcharge()))
                }
            ) {
                Row() {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = Icons.Default.Add.name,
                    )
                    
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))

                    Text("Thêm")
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewStep1Screen() {
    InnitScreen()
}

@Preview(showBackground = true)
@Composable
fun PreviewStep3Screen() {
    Step3()
}

