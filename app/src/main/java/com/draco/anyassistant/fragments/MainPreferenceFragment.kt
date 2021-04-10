package com.draco.anyassistant.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.draco.anyassistant.R
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.snackbar.Snackbar

class MainPreferenceFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var reset: Preference
    private lateinit var pick: Preference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.main, rootKey)
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        reset = findPreference(getString(R.string.pref_reset_key))!!
        pick = findPreference(getString(R.string.pref_pick_key))!!

        refreshSettings()
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        when (preference.key) {
            getString(R.string.pref_developer_key) -> openURL(getString(R.string.developer_url))
            getString(R.string.pref_source_key) -> openURL(getString(R.string.source_url))
            getString(R.string.pref_contact_key) -> openURL(getString(R.string.contact_url))
            getString(R.string.pref_licenses_key) -> {
                val intent = Intent(requireContext(), OssLicensesMenuActivity::class.java)
                startActivity(intent)
            }
            else -> return super.onPreferenceTreeClick(preference)
        }
        return true
    }

    /**
     * Update the UI to show the new constants
     */
    private fun refreshSettings() {
        //interval.value = Settings.Global.getString(requireContext().contentResolver, SettingsConstants.FSTRIM_MANDATORY_INTERVAL) ?: "null"
    }

    /**
     * Take the UI settings and apply them as constants
     */
    private fun applySettings() {
        //Settings.Global.putString(requireContext().contentResolver, SettingsConstants.FSTRIM_MANDATORY_INTERVAL, interval.value)
    }

    /**
     * Open a URL for the user
     */
    private fun openURL(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        try {
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            Snackbar.make(requireView(), getString(R.string.snackbar_intent_failed), Snackbar.LENGTH_SHORT).show()
        }
    }

    /**
     * When settings are changed, apply the new config
     */
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        applySettings()
    }
}