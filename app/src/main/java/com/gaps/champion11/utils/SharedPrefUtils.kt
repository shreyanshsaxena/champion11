
package com.gaps.champion11.utils
import android.content.Context
import android.content.SharedPreferences
import com.gaps.champion11.BaseApplication

class SharedPrefUtils{
    companion object {
         const val AppPreference = "PREFERENCES"
        const val KEY_TOKEN = "keytoken"
        const val SELECTED_GAME_ID = "selectedgameid"
        const val SELECTED_NO = "selectedno"
        const val USER_NAME = "username"
        const val USER_DETAILS = "userdetails"
        const val USER_ID = "userid"
        private val preferences: SharedPreferences = BaseApplication.appContext!!.getSharedPreferences(
                AppPreference,
                Context.MODE_PRIVATE
            )

        fun getString(context: Context?, key: String?, defaultValue: String?): String? {
            return preferences.getString(key, defaultValue)
        }

        fun getBoolean(context: Context, key: String?): Boolean {
            return preferences.getBoolean(key, false)
        }

        fun setBoolean(context: Context, key: String?, value: Boolean) {
            val editor = preferences.edit()
            editor.putBoolean(key, value)
            editor.apply()
        }

        fun setLong(context: Context, key: String?, value: Long) {
            val editor = preferences.edit()
            editor.putLong(key, value)
            editor.apply()
        }

        fun setInt(context: Context, key: String?, value: Int) {
            val editor = preferences.edit()
            editor.putInt(key, value)
            editor.apply()
        }

        fun getInt(context: Context, key: String?): Int {
            return preferences.getInt(key, Int.MIN_VALUE).toInt()
        }

        fun getLong(context: Context, key: String?): Long {
            return preferences.getLong(key, 0)
        }

        fun clearPreferencesData(ctx: Context) {
            setString(ctx, KEY_TOKEN, null)
            setString(ctx, USER_NAME, null)
            setString(ctx, USER_ID, null)
        }

        fun setString(context: Context, key: String?, value: String?) {
            val editor = preferences.edit()
            editor.putString(key, value)
            editor.apply()
        }

        fun clearAll(context: Context) {
            val editor = preferences.edit()
            editor.clear()
            editor.apply()
        }
    }
}
