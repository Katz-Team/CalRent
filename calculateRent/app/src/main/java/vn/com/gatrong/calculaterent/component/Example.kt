import android.widget.Space
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun example() {

    // android:windowSoftInputMode="adjustResize"

    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val focusRequesters = remember { List( 20 ) { FocusRequester() } }
    // Setup the handles to items to scroll to.
    val bringIntoViewRequesters = mutableListOf(remember { BringIntoViewRequester() })
    repeat(20) {
        bringIntoViewRequesters += remember { BringIntoViewRequester() }
    }
    val buttonViewRequester = remember { BringIntoViewRequester() }
    val scrollState = rememberScrollState()

    fun requestBringIntoView(focusState: FocusState, viewItem: Int) {
        if (focusState.isFocused) {
            coroutineScope.launch {
                delay(200) // needed to allow keyboard to come up first.
                if (viewItem >= 2) { // force to scroll to button for lower fields
                    buttonViewRequester.bringIntoView()
                } else {
//                    bringIntoViewRequesters[viewItem].bringIntoView()
                }
            }
        }
    }

    fun requestBringIntoView(viewItem: Int) {
        coroutineScope.launch {
//            delay(200) // needed to allow keyboard to come up first.
            if (viewItem >= 2) { // force to scroll to button for lower fields
                buttonViewRequester.bringIntoView()
            } else {
                    bringIntoViewRequesters[viewItem].bringIntoView()
            }
        }
    }
    

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .padding(10.dp)
    ) {
        LazyColumn {
            items(20) { viewItem ->

                val s1 = remember { mutableStateOf("") }
                Row(
                    modifier = Modifier,
                ) {
                    Text(text = viewItem.toString())

                    TextField(
                        value = s1.value,
                        onValueChange = {s1.value = it},
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusRequesters.get(viewItem + 1).requestFocus()

                                coroutineScope.launch {
                                    bringIntoViewRequesters[viewItem + 3].bringIntoView()
//                                scrollState.animateScrollTo(scrollState.maxValue)
                                }
                            }),
                        modifier = Modifier
                            .focusRequester(focusRequesters.get(viewItem))
                            .bringIntoViewRequester(bringIntoViewRequesters[viewItem])
                            .onFocusEvent { focusState ->
//                            requestBringIntoView(focusState, viewItem)
                            },
                    )
                }
            }
        }



        Button(
            onClick = {},
            modifier = Modifier
                .bringIntoViewRequester(buttonViewRequester)
        ) {
            Text(text = "I'm Visible")
        }
    }
}


@Preview
@Composable
fun examplePreview() {
    example()
}