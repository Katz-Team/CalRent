package vn.com.gatrong.calculaterent.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class Navigator {

    companion object {
        private val _stateScreen = MutableStateFlow<NavigateState>(NavigateState(NavigateState.FEED_SCREEN))
        val stateScreen = _stateScreen.asStateFlow()

        lateinit var data : Any

        fun navigateTo(navigateState: NavigateState) {
            _stateScreen.update {
                it.copy(navigateState.label)
            }
        }

        fun navigateTo(navigateState: NavigateState, data : Any) {
            this.data = data
            navigateTo(navigateState = navigateState)
        }

        fun back() {
            navigateTo(navigateState = NavigateState(NavigateState.BACK))
        }
    }
}