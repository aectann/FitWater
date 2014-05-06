package io.github.aectann.fitwater;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;


public class MainActivity extends BaseActivity {


  @Inject
  OAuthService service;

  @Inject
  RequestTokenHolder tokenHolder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);
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

  @OnClick(R.id.fitbit_button)
  public void fitbit(View v) {
    new AsyncTask<Void, Void, Token>() {

      @Override
      protected Token doInBackground(Void... params) {
        Token reqToken = service.getRequestToken();
        Timber.d(String.valueOf(reqToken));
        return reqToken;
      }

      @Override
      protected void onPostExecute(Token token) {
        tokenHolder.setRequestToken(token);
        String authorizationUrl = service.getAuthorizationUrl(token);
        Timber.d(authorizationUrl);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(authorizationUrl));
        startActivityForResult(intent, 0);
      }
    }.execute();
  }
}
