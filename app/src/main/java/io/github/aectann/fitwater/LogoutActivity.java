package io.github.aectann.fitwater;

import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;


public class LogoutActivity extends BaseActivity {

  @Inject
  CredentialsStore credentialsStore;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    credentialsStore.setAccessToken(null);
    startActivity(new Intent(this, MainActivity.class));
    finish();
  }
}
