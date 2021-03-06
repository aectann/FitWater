package io.github.aectann.fitwater.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;

import io.github.aectann.fitwater.R;

/**
* Created by aectann on 14/05/14.
*/
public class SettingsFragment extends PreferenceFragment {

  private static final String AUTO_SUMMARY_SUFFIX = "_auto_summary";

  private SharedPreferences.OnSharedPreferenceChangeListener autoSummaryListener;

  public SettingsFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.pref_general);
    int preferenceCount = getPreferenceScreen().getPreferenceCount();
    for (int i = 0; i < preferenceCount; i++) {
      Preference preference = getPreferenceScreen().getPreference(i);
      String key = preference.getKey();
      if (key != null && key.endsWith(AUTO_SUMMARY_SUFFIX)) {
        onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(preference.getKey(), ""));
      }
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    PreferenceManager.getDefaultSharedPreferences(getActivity()).unregisterOnSharedPreferenceChangeListener(autoSummaryListener);
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    autoSummaryListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
      @Override
      public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.endsWith(AUTO_SUMMARY_SUFFIX)) {
          onPreferenceChange(getPreferenceScreen().findPreference(key), sharedPreferences.getString(key, ""));
        }
      }
    };
    PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(autoSummaryListener);
  }

  private boolean onPreferenceChange(Preference preference, String stringValue) {

    if (preference instanceof ListPreference) {
      // For list preferences, look up the correct display value in
      // the preference's 'entries' list.
      ListPreference listPreference = (ListPreference) preference;
      int index = listPreference.findIndexOfValue(stringValue);

      // Set the summary to reflect the new value.
      preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);

    } else if (preference instanceof RingtonePreference) {
      // For ringtone preferences, look up the correct display value
      // using RingtoneManager.
      if (TextUtils.isEmpty(stringValue)) {
        // Empty values correspond to 'silent' (no ringtone).
        preference.setSummary(R.string.pref_ringtone_silent);

      } else {
        Ringtone ringtone = RingtoneManager.getRingtone(preference.getContext(), Uri.parse(stringValue));
        if (ringtone == null) {
          // Clear the summary if there was a lookup error.
          preference.setSummary(null);
        } else {
          // Set the summary to reflect the new ringtone display name.
          String name = ringtone.getTitle(preference.getContext());
          preference.setSummary(name);
        }
      }

    } else {
      // For all other preferences, set the summary to the value's
      // simple string representation.
      preference.setSummary(stringValue);
    }
    return false;
  }

}
