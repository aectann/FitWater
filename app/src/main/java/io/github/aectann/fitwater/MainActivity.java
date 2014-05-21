package io.github.aectann.fitwater;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.scribe.model.Token;

import javax.inject.Inject;

import io.github.aectann.fitwater.fragments.IntakeFragment;
import io.github.aectann.fitwater.fragments.LoginFragment;


public class MainActivity extends BaseActivity {

  private static final String TOKEN_AVAILABLE = "token-available";

  @Inject
  CredentialsStore credentialsStore;

  private boolean tokenAvailable;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_data);
    Token accessToken = credentialsStore.getAccessToken();
    boolean showLogin = accessToken == null;
    if (savedInstanceState == null) {
      replaceFragment(showLogin);
    } else {
      tokenAvailable = savedInstanceState.getBoolean(TOKEN_AVAILABLE);
      if (tokenAvailable ^ (accessToken != null)) {
        replaceFragment(showLogin);
      }
    }
    tokenAvailable = accessToken != null;
  }

  private void replaceFragment(boolean showLogin) {
    Fragment fragment = showLogin ? new LoginFragment() : new IntakeFragment();
    getFragmentManager().beginTransaction()
            .replace(R.id.container, fragment)
            .commit();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putBoolean(TOKEN_AVAILABLE, tokenAvailable);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    if (tokenAvailable) {
      getMenuInflater().inflate(R.menu.main, menu);
    }
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      startActivity(new Intent(this, SettingsActivity.class));
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

}
