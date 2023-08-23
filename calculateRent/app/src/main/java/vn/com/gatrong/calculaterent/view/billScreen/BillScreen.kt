package vn.com.gatrong.calculaterent.view.billScreen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import vn.com.gatrong.calculaterent.extensions.formatToMoney
import vn.com.gatrong.calculaterent.extensions.toDateString
import vn.com.gatrong.calculaterent.model.Bill
import vn.com.gatrong.calculaterent.model.Surcharge
import vn.com.gatrong.calculaterent.navigation.Navigator
import vn.com.gatrong.calculaterent.navigation.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillScreen() {
    val screen = (Navigator.getBundle(Screen.NavigateState.BILL_SCREEN.toString()) as Screen.BillScreen)

    val bill = screen.bill
    val modeView = screen.modeView
    val viewModel = viewModel<BillViewModel>()

    Scaffold(Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Tiền trọ",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { Navigator.back() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = Icons.Default.ArrowBack.name)
                    }
                }
            )
        },
        content = {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp, bottom = 60.dp)) {

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)) {

                    Text(
                        text = "${bill.timeFrom.toDateString()} - ${bill.timeTo.toDateString()}", modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleMedium,
                    )

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(0.15f),
                            text = "Điện:",
                            textAlign = TextAlign.Left,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(
                            modifier = Modifier.fillMaxWidth(0.7f),
                            text = "(${bill.electricityBill.newElectric} - ${bill.electricityBill.preElectric})x${bill.electricityBill.price.formatToMoney()}",
                            textAlign = TextAlign.Left,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = bill.electricityBill.getMoney().formatToMoney(),
                            textAlign = TextAlign.Right,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(0.15f),
                            text = "Nước:",
                            textAlign = TextAlign.Left,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(
                            modifier = Modifier.fillMaxWidth(0.7f),
                            text = "(${bill.waterBill.newWater} - ${bill.waterBill.preWater})x${bill.waterBill.price.formatToMoney()}",
                            textAlign = TextAlign.Left,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(modifier = Modifier.fillMaxWidth(),
                            text = bill.waterBill.getMoney().formatToMoney(),
                            textAlign = TextAlign.Right,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    for (surcharge in bill.surcharges) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                modifier = Modifier.fillMaxWidth(0.7f),
                                text = "${surcharge.name}:",
                                textAlign = TextAlign.Left,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = surcharge.price.formatToMoney(),
                                textAlign = TextAlign.Right,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(0.7f),
                            text = "Tiền nhà:",
                            textAlign = TextAlign.Left,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(modifier = Modifier.fillMaxWidth(),
                            text = bill.moneyRent.formatToMoney(),
                            textAlign = TextAlign.Right,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = bill.getTotalMoney().formatToMoney(),
                        textAlign = TextAlign.Right,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.titleLarge
                    )

                }

                if (!modeView) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .align(Alignment.BottomCenter),
                        onClick = {
                            viewModel.insertBill(bill).let {
                                Navigator.back()
                            }
                        }
                    ) { Text("Hoàn tất") }
                }
            }
        }
    )

    BackHandler {
        Navigator.back()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBillScreen() {
    BillScreen()
}