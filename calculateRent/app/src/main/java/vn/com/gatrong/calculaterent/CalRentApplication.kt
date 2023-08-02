package vn.com.gatrong.calculaterent

import android.app.Application
import vn.com.gatrong.calculaterent.dependencyInjection.DependencyInjector

class CalRentApplication : Application() {

    lateinit var dependencyInjector: DependencyInjector

    override fun onCreate() {
        super.onCreate()
        dependencyInjector = DependencyInjector(this)
        dependencyInjector.repository
    }
}