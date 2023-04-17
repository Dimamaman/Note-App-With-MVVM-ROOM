package uz.gita.dimanote.data.source.local.sharedPref

import android.content.Context
import android.content.SharedPreferences

class MySharedPreference private constructor(context: Context) {

    private val sharedPreference: SharedPreferences =
        context.getSharedPreferences("Shared", Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = sharedPreference.edit()


    companion object {
        private lateinit var instance: MySharedPreference

        fun init(context: Context) {
            instance = MySharedPreference(context)
        }

        fun getInstance(): MySharedPreference = instance
    }

    var isNight: Boolean
        get() = sharedPreference.getBoolean("isNight", true)
        set(value) = editor.putBoolean("isNight", value).apply()
}