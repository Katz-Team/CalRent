package vn.com.gatrong.calculaterent.navigation

data class NavigateState(val label : String) {
    companion object {
        const val INNIT_SCREEN = "INNIT_SCREEN"
        const val FEED_SCREEN = "FEED_SCREEN"
        const val CAL_SCREEN = "CAL_SCREEN"
        const val BILL_SCREEN = "BILL_SCREEN"
        const val BACK = "BACK"
    }
}
