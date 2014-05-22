package io.github.aectann.fitwater;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import org.scribe.model.Token;

import io.github.aectann.fitwater.loaders.AccessTokenLoader;
import io.github.aectann.fitwater.loaders.RequestResult;

/**
 * Created by aectann on 4/05/14.
 */
public class OAuthCallbackActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<RequestResult<Token>> {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_oauth_callback);
    getLoaderManager().initLoader(0, getIntent().getExtras(), this);
  }

  @Override
  public Loader<RequestResult<Token>> onCreateLoader(int id, Bundle args) {
    return new AccessTokenLoader(this);
  }

  @Override
  public void onLoadFinished(Loader<RequestResult<Token>> loader, RequestResult<Token> data) {
    if (data.hasError()) {
      Toast.makeText(this, data.getErrorMessage(), Toast.LENGTH_LONG).show();
    }
    startActivity(new Intent(OAuthCallbackActivity.this, MainActivity.class));
    finish();
  }

  @Override
  public void onLoaderReset(Loader<RequestResult<Token>> loader) {
  }
}
