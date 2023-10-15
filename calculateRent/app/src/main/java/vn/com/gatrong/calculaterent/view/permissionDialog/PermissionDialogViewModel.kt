package vn.com.gatrong.calculaterent.view.permissionDialog

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class PermissionDialogViewModel : ViewModel() {

    // [RECORD-AUDIO, CAMERA]
    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeLast()
    }

    fun onPermissionResult(permission: String, isGranted: Boolean) {
       if (!isGranted) {
           visiblePermissionDialogQueue.add(0, permission)
       }
    }

}