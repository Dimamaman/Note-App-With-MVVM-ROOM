package uz.gita.dimanote.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import uz.gita.dimanote.data.source.local.NoteDatabase
import uz.gita.dimanote.data.source.local.sharedPref.MySharedPreference

class App: Application() {
    override fun onCreate() {
        super.onCreate()
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        MySharedPreference.init(this)
        NoteDatabase.init(this)
    }
}