package guuilp.github.com.traktstudy

import android.app.Application
import guuilp.github.com.traktstudy.di.appModule
import org.koin.android.ext.android.startKoin

class TraktStudyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }
}