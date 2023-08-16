package vn.com.gatrong.calculaterent.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class Navigator {

    companion object {
        private val stateNavigate = MutableStateFlow<Screen>(Screen.FeedScreen())

        var stackBundle = ArrayList<Pair<String, Screen>>()

        fun navigateTo(navigateState: Screen) {
            stateNavigate.update {
                navigateState
            }
        }

        fun getNavigate() = stateNavigate.asStateFlow()

        fun pushBundle(key: String, screen: Screen) {
            stackBundle.add(Pair(key, screen))
        }

        fun getBundle(key: String): Screen? {
            val index = stackBundle.indexOfLast { it.first == key }
            if (index != -1) {
                return stackBundle[index].second
            }
            return null
        }

        fun popBundle(key: String): Screen? {
            val index = stackBundle.indexOfLast { it.first == key }
            if (index != -1) {
                val value = stackBundle[index].second
                stackBundle.removeAt(index)
                return value
            }
            return null
        }

        fun back() {
            navigateTo(navigateState = Screen.Back())
        }
    }
}