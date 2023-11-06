package vn.com.gatrong.calculaterent

import android.os.Bundle
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
import vn.com.gatrong.calculaterent.navigation.Screen
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
        Navigator.getNavigate().collect { navigateState ->
            if (navigateState.route.equals(Screen.NavigateState.BACK.toString())) {
                Navigator.popBundle(navController.currentBackStackEntry?.destination?.route.toString())
                navController.popBackStack()
            } else {
                Navigator.pushBundle(navigateState.route, navigateState)
                navController.navigate(navigateState.route) {
                    if (navigateState.route.equals(Screen.NavigateState.BILL_SCREEN.toString())) {
                        popUpTo(Screen.NavigateState.CAL_SCREEN.toString()) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    NavHost(navController = navController, startDestination = Screen.NavigateState.FEED_SCREEN.toString()) {
        composable(Screen.NavigateState.INNIT_SCREEN.toString()) { InnitScreen() }
        composable(Screen.NavigateState.FEED_SCREEN.toString()) { FeedScreen() }
        composable(Screen.NavigateState.CAL_SCREEN.toString()) { CalScreen() }
        composable(Screen.NavigateState.BILL_SCREEN.toString()) { BillScreen() }
    }
}
