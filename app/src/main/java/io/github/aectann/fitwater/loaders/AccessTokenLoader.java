package io.github.aectann.fitwater.loaders;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import org.scribe.model.Token;
import org.scribe.model.Verifier;

import hugo.weaving.DebugLog;
import io.github.aectann.fitwater.OAuthCallbackActivity;

/**
 * Created by aectann on 7/05/14.
 */
public class AccessTokenLoader extends BaseAsyncTaskLoader<Token> {

  private final String verifier;

  @DebugLog
  public AccessTokenLoader(Activity context) {
    super(context);
    Uri data = context.getIntent().getData();
    this.verifier = data.getQueryParameter("oauth_verifier");
  }

  @Override
  @DebugLog
  public Token loadInBackground() {
    Token accessToken = service.getAccessToken(credentialsStore.getRequestToken(), new Verifier(verifier));
    credentialsStore.setRequestToken(null);
    credentialsStore.setAccessToken(accessToken);
    return data = accessToken;
  }

}
