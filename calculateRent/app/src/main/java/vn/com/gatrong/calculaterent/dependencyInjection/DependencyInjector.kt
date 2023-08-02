package vn.com.gatrong.calculaterent.dependencyInjection

import android.content.Context
import androidx.room.Room
import vn.com.gatrong.calculaterent.data.AppDatabase
import vn.com.gatrong.calculaterent.data.repository.Repository
import vn.com.gatrong.calculaterent.data.repository.RepositoryImpl

class DependencyInjector(application: Context) {

    val repository : Repository by lazy { RepositoryImpl.getInstance(application) }
}