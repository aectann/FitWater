package io.github.aectann.fitwater;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by aectann on 4/05/14.
 */
public class OAuthCallbackActivity extends BaseActivity {

  @Inject
  OAuthService service;

  @Inject
  RequestTokenHolder tokenHolder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Uri data = getIntent().getData();
    Timber.d("OAuth: " + data);
    final String oauth_token = data.getQueryParameter("oauth_token");
    final String oauth_verifier = data.getQueryParameter("oauth_verifier");
    new AsyncTask<Void, Void, Token>() {

      @Override
      protected Token doInBackground(Void... params) {
        Token accessToken = service.getAccessToken(tokenHolder.getRequestToken(), new Verifier(oauth_verifier));
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.fitbit.com/1/user/-/foods/log/water/goal.json");
        service.signRequest(accessToken, request);
        String body = request.send().getBody();
        Timber.d("Goal: " + body);
        return accessToken;
      }

      @Override
      protected void onPostExecute(Token token) {
        Timber.d("Access token: " + token.getToken());
      }
    }.execute();

  }
}
