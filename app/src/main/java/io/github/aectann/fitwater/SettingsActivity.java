package io.github.aectann.fitwater;

import android.app.Activity;
import android.os.Bundle;

import io.github.aectann.fitwater.fragments.SettingsFragment;

public class SettingsActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    if (savedInstanceState == null) {
      getFragmentManager().beginTransaction().add(R.id.container, new SettingsFragment()).commit();
    }
  }


}
