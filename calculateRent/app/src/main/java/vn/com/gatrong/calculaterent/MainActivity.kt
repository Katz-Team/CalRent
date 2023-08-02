package vn.com.gatrong.calculaterent

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import vn.com.gatrong.calculaterent.navigation.NavigateState
import vn.com.gatrong.calculaterent.navigation.Navigator
import vn.com.gatrong.calculaterent.ui.theme.CalculateRentTheme
import vn.com.gatrong.calculaterent.view.FeedScreen
import vn.com.gatrong.calculaterent.view.billScreen.BillScreen
import vn.com.gatrong.calculaterent.view.calScreen.CalScreen
import vn.com.gatrong.calculaterent.view.innitRoomScreen.InnitScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculateRentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationView()
                }
            }
        }
    }
}

@Composable
fun NavigationView() {
    val navController = rememberNavController()

    LaunchedEffect(navController::class) {
        Navigator.stateScreen.collect {
            if (it.label.equals(NavigateState.BACK)) {
                navController.popBackStack()
            } else {
                navController.navigate(it.label) {
                    if (it.label.equals(NavigateState.BILL_SCREEN)) {
                        popUpTo(NavigateState.CAL_SCREEN) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    NavHost(navController = navController, startDestination = NavigateState.FEED_SCREEN) {
        composable(NavigateState.INNIT_SCREEN) { InnitScreen() }
        composable(NavigateState.FEED_SCREEN) { FeedScreen() }
        composable(NavigateState.CAL_SCREEN) { CalScreen() }
        composable(NavigateState.BILL_SCREEN) { BillScreen() }
    }
}
