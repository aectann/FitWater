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
import io.github.aectann.fitwater.io.UserLoggedIn;
import io.github.aectann.fitwater.rx.GlobalUiEvents;


public class MainActivity extends BaseActivity {

  private static final String TOKEN_AVAILABLE = "token-available";

  @Inject
  @UserLoggedIn
  boolean tokenAvailable;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_data);
    if (savedInstanceState == null) {
      replaceFragment();
    } else {
      boolean prevTokenAvailable = savedInstanceState.getBoolean(TOKEN_AVAILABLE);
      if (tokenAvailable ^ prevTokenAvailable) {
        replaceFragment();
      }
    }
  }

  private void replaceFragment() {
    Fragment fragment = tokenAvailable ? new IntakeFragment() : new LoginFragment();
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
    } else if (id == R.id.refresh) {
      GlobalUiEvents.REFRESH.fire();
    }
    return super.onOptionsItemSelected(item);
  }

}
