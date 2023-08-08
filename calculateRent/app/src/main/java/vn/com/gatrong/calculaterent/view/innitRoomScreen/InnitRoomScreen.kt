package vn.com.gatrong.calculaterent.view.innitRoomScreen

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import vn.com.gatrong.calculaterent.R
import vn.com.gatrong.calculaterent.extensions.formatToMoney
import vn.com.gatrong.calculaterent.extensions.formatToMoneyString
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

            Text(stringResource(id = R.string.initRoom), style = MaterialTheme.typography.headlineLarge)


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
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        label = { Text("Ngày bắt đầu") },
        value = viewModel.time.collectAsStateWithLifecycle().value,
        onValueChange = {
            viewModel.time.value = it
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
        })
    )

    OutlinedTextField(label = { Text("Tiền thuê phòng") },
        value = viewModel.rentHouse.collectAsStateWithLifecycle().value.formatToMoney(),
        onValueChange = {
            viewModel.rentHouse.value = it.formatToMoneyString()
        },
        singleLine = true,
        trailingIcon = { Text("VND") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Step2() {
    val viewModel = viewModel<InnitRoomViewModel>()
    val focusManager = LocalFocusManager.current
    OutlinedTextField(label = { Text("Giá điện") },
        value = viewModel.rentElect.collectAsStateWithLifecycle().value.formatToMoney(),
        onValueChange = { viewModel.rentElect.value = it.formatToMoneyString()  },
        trailingIcon = { Text("VND") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        )
    )

    OutlinedTextField(label = { Text("Khối điện khởi điểm") },
        value = viewModel.kgElect.collectAsStateWithLifecycle().value,
        onValueChange = { viewModel.kgElect.value = if (it.isDigitsOnly()) it else viewModel.kgElect.value },
        trailingIcon = { Text("Kwh") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        )
    )

    OutlinedTextField(label = { Text("Giá nước") },
        value = viewModel.rentWater.collectAsStateWithLifecycle().value.formatToMoney(),
        onValueChange = { viewModel.rentWater.value = it.formatToMoneyString()  },
        trailingIcon = { Text("VND") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        )
    )

    OutlinedTextField(label = { Text("Khối nước khởi điểm") },
        value = viewModel.kgWater.collectAsStateWithLifecycle().value,
        onValueChange = { viewModel.kgWater.value = if (it.isDigitsOnly()) it else viewModel.kgWater.value },
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
    val focusManager = LocalFocusManager.current

    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)) {

        items(viewModel.defaultSurcharges.size) { index ->
            val defaultSurcharge = viewModel.defaultSurcharges.get(index).collectAsStateWithLifecycle().value
            OutlinedTextField(label = { Text("${defaultSurcharge.name} ${index + 1}") },
                value = defaultSurcharge.price.formatToMoney(),
                onValueChange = {
                    viewModel.defaultSurcharges.get(index).value = DefaultSurcharge(
                        price = if (it.isNotEmpty()) it.formatToMoneyString().toLong() else 0
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = if (index + 1 < viewModel.defaultSurcharges.size) ImeAction.Next else ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )
        }

        item {
            Button(modifier = Modifier
                .fillMaxWidth(0.7f)
                ,onClick = {
                    viewModel.defaultSurcharges.add(MutableStateFlow(DefaultSurcharge()))
                }
            ) { Text("Thêm") }
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

