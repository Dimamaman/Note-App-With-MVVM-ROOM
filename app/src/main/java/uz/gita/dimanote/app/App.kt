package uz.gita.dimanote.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import uz.gita.dimanote.data.source.local.NoteDatabase

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        NoteDatabase.init(this)
    }
}