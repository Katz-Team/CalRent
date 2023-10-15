package vn.com.gatrong.calculaterent.view.cameraScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import vn.com.gatrong.calculaterent.navigation.Navigator
@Composable
fun CameraScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(20.0f)
            .background(Color.White)
    ) {
        Text(
            text = "Camera Screen",
            color = Color.Blue,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
        Button(onClick = {
            Navigator.back()
        }
        ) {
            Text(text = "Back")
        }
    }
}


