package vn.com.gatrong.calculaterent.view.permissionDialog

import android.Manifest
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    //onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier
) {
    val cameraPermission = Manifest.permission.CAMERA
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
           Column(
               modifier = Modifier.fillMaxWidth()
           ) {
               Divider()
               Text(
                   text = if (isPermanentlyDeclined) "Granted permission" else "OK",
                   fontWeight = FontWeight.Bold,
                   textAlign = TextAlign.Center,
                   modifier = Modifier
                       .fillMaxWidth()
                       .clickable {
                           if (isPermanentlyDeclined) {
                               //onGoToAppSettingsClick()
                           } else {
                               onOkClick()
                           }
                       }
                       .padding(16.dp)

               )
           }
        },
        title = {
            Text(text = "Permission required")
        },
        text = {
            Text(
                text = permissionTextProvider.getDescription(
                    isPermanentlyDeclined = isPermanentlyDeclined
                )
            )
        },
        modifier = modifier
    )
}

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}

class CameraPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if(isPermanentlyDeclined) {
            "It seems you permanently declined camera permission. " +
                    "You can go to the app settings to grant it."

        } else {
            "This app needs access to your camera so that your friends " +
                    "can see you."
        }
    }

}