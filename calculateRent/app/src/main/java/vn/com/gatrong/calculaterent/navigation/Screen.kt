package vn.com.gatrong.calculaterent.navigation

import vn.com.gatrong.calculaterent.model.Bill

sealed class Screen(val route : String) {
    enum class NavigateState() {
        INNIT_SCREEN,
        FEED_SCREEN,
        CAL_SCREEN,
        BILL_SCREEN,
        BACK
    }

    class InnitScreen : Screen(NavigateState.INNIT_SCREEN.toString())
    class FeedScreen : Screen(NavigateState.FEED_SCREEN.toString())
    class CalScreen : Screen(NavigateState.CAL_SCREEN.toString())
    class BillScreen(val bill: Bill, val modeView : Boolean = true) : Screen(NavigateState.BILL_SCREEN.toString())
    class Back : Screen(NavigateState.BACK.toString())
}
