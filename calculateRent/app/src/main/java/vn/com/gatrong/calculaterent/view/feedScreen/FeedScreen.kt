package vn.com.gatrong.calculaterent.view

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import vn.com.gatrong.calculaterent.extensions.formatToMoney
import vn.com.gatrong.calculaterent.extensions.toDateString
import vn.com.gatrong.calculaterent.navigation.Screen
import vn.com.gatrong.calculaterent.navigation.Navigator
import vn.com.gatrong.calculaterent.view.feedScreen.FeedViewModel

@SuppressLint("UnrememberedMutableState", "MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen() {

    val viewModel = viewModel<FeedViewModel>()
    var bills = viewModel.getRoom().collectAsStateWithLifecycle()
    var expanded by remember { mutableStateOf(false) }
    var editMode by remember { mutableStateOf(false) }
    val selected = remember { mutableStateOf(setOf<Long>()) }

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
                                DropdownMenuItem(
                                    text = { Text(text = "Chỉnh sửa") },
                                    onClick = {
                                        editMode = !editMode
                                        expanded = false
                                    }
                                )
                            }
                        }

                    )

                },
                navigationIcon = {
                    if (editMode) {
                        IconButton(
                            content = {
                                Icon(
                                    Icons.Default.ArrowBack,
                                    contentDescription = Icons.Default.ArrowBack.toString()
                                )
                            },
                            onClick = {
                                editMode = false
                                selected.value = setOf<Long>()
                            }
                        )
                    }
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
                            leadingContent = {
                                 if (editMode) {
                                     Checkbox(
                                         checked = selected.value.contains(bills.value.bills.get(index).id),
                                         onCheckedChange = { isChecked ->
                                             if (selected.value.contains(bills.value.bills.get(index).id)) {
                                                 selected.value -= bills.value.bills.get(index).id
                                             } else {
                                                 selected.value += bills.value.bills.get(index).id
                                             }
                                         }
                                     )
                                 }
                            },
                            modifier = Modifier.clickable {
                                if (editMode) {
                                    if (selected.value.contains(bills.value.bills.get(index).id)) {
                                        selected.value -= bills.value.bills.get(index).id
                                    } else {
                                        selected.value += bills.value.bills.get(index).id
                                    }
                                } else {
                                    Navigator.navigateTo(
                                        Screen.BillScreen(bills.value.bills.get(index))
                                    )
                                }
                            }
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            if (!editMode) {
                LargeFloatingActionButton(
                    onClick = {
                        Navigator.navigateTo(Screen.CalScreen())
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
        },
        bottomBar = {
            if (editMode && selected.value.isNotEmpty()) {
                BottomAppBar {
                    IconButton(
                        content = {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = Icons.Default.Delete.toString()
                            )
                        },
                        onClick = {
                            selected.value.forEach { item ->
                                bills.value.bills.find { it.id == item }?.let { billSelected ->
                                    viewModel.deleteBill(billSelected)
                                }
                            }
                        }
                    )
                }
            }
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

