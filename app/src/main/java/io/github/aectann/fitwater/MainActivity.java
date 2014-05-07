package io.github.aectann.fitwater;

import android.app.Fragment;
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
    if (savedInstanceState == null) {
      Fragment fragment = accessToken == null ? new LoginFragment() : new IntakeFragment();
      getFragmentManager().beginTransaction()
              .add(R.id.container, fragment)
              .commit();
    } else {
      tokenAvailable = savedInstanceState.getBoolean(TOKEN_AVAILABLE);
      if (tokenAvailable ^ (accessToken != null)) {
        Fragment fragment = accessToken == null ? new LoginFragment() : new IntakeFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
      }
    }
    tokenAvailable = accessToken != null;
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putBoolean(TOKEN_AVAILABLE, tokenAvailable);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

}
