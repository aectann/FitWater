package io.github.aectann.fitwater;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;

import org.scribe.model.Token;

import io.github.aectann.fitwater.loaders.AccessTokenLoader;

/**
 * Created by aectann on 4/05/14.
 */
public class OAuthCallbackActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Token> {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getLoaderManager().initLoader(0, getIntent().getExtras(), this);
  }

  @Override
  public Loader<Token> onCreateLoader(int id, Bundle args) {
    return new AccessTokenLoader(this);
  }

  @Override
  public void onLoadFinished(Loader<Token> loader, Token data) {
    startActivity(new Intent(OAuthCallbackActivity.this, MainActivity.class));
    finish();
  }

  @Override
  public void onLoaderReset(Loader<Token> loader) {
  }
}
