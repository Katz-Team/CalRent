package vn.com.gatrong.calculaterent.view

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import vn.com.gatrong.calculaterent.extensions.formatToMoney
import vn.com.gatrong.calculaterent.extensions.toDateString
import vn.com.gatrong.calculaterent.navigation.NavigateState
import vn.com.gatrong.calculaterent.navigation.Navigator
import vn.com.gatrong.calculaterent.view.feedScreen.FeedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen() {

    val viewModel = viewModel<FeedViewModel>()
    var bills = viewModel.getRoom().collectAsStateWithLifecycle()
    var expanded by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Tiền trọ", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                actions = {
                    IconButton(
                        onClick = {
                            expanded = true
                        },
                        content = {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = Icons.Default.MoreVert.name
                            )

                            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                DropdownMenuItem(text = { Text(text = "Chỉnh sửa") }, onClick = {  })
                            }
                        }

                    )

                }
            )

        },
        content = { paddingValues ->
            LazyColumn(Modifier.padding(paddingValues)) {
                items(bills.value.bills.size) { index ->
                    if (index != bills.value.bills.size - 1) {
                        ListItem(
                            overlineText = { Text(text = "${bills.value.bills.get(index).timeFrom.toDateString()} - ${bills.value.bills.get(index).timeTo.toDateString()}") },
                            headlineText = { Text(text = bills.value.bills.get(index).getTotalMoney().formatToMoney()) },
                            supportingText = { Text(text = """
                            Điện: ${bills.value.bills.get(index).electricityBill.getMoney().formatToMoney()}
                            Nước: ${bills.value.bills.get(index).waterBill.getMoney().formatToMoney()}
                                                        """.trimIndent()) },
                            modifier = Modifier.clickable {
                                Navigator.navigateTo(NavigateState(NavigateState.BILL_SCREEN),bills.value.bills.get(index))
                            }
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = {
                    Navigator.navigateTo(NavigateState(NavigateState.CAL_SCREEN))
                },
                content = {
                    Icon(
                        Icons.Outlined.Create,
                        contentDescription = "Create",
                        modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize)
                    )
                }
            )
        }
    )

    BackHandler {

    }

}

@Preview(showBackground = true)
@Composable
fun PreviewScreenA() {
    FeedScreen()
}

