package com.example.submissionfundamental.ui.setting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.submissionfundamental.R
import com.example.submissionfundamental.ui.favorite.FavoriteActivity

class SettingFragment : PreferenceFragmentCompat() {

    private lateinit var mainViewModel: MainViewModel
    private var themePreference: SwitchPreference? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)

        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        mainViewModel = ViewModelProvider(this, VIewModelFactory(pref))[MainViewModel::class.java]

        themePreference = findPreference<SwitchPreference>("themeKey")

        val favoritePreference: Preference? = findPreference("favoriteKey")
        favoritePreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            // Intent untuk berpindah ke FavoriteActivity
            val intent = Intent(activity, FavoriteActivity::class.java)
            startActivity(intent)
            true
        }

        mainViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            themePreference?.let { switchPref ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchPref.isChecked = true
                    updateThemeIcon(R.drawable.ic_lightmode)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchPref.isChecked = false
                    updateThemeIcon(R.drawable.ic_darkmode)
                }
            }
        }

        themePreference?.setOnPreferenceChangeListener { _, newValue ->
            val isChecked = newValue as Boolean
            mainViewModel.saveThemeSetting(isChecked)
            true
        }
    }

    private fun updateThemeIcon(iconResId: Int) {
        themePreference?.icon = resources.getDrawable(iconResId, null)
    }
}
