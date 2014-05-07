package io.github.aectann.fitwater.loaders;

import android.content.Context;

import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import javax.inject.Inject;

import io.github.aectann.fitwater.CredentialsStore;
import timber.log.Timber;

/**
 * Created by aectann on 7/05/14.
 */
public class AccessTokenLoader extends BaseAsyncTaskLoader<Token> {

  private final String verifier;

  @Inject
  OAuthService service;

  @Inject
  CredentialsStore tokenHolder;

  public AccessTokenLoader(Context context, String verifier) {
    super(context);
    this.verifier = verifier;
  }

  @Override
  public Token loadInBackground() {
    Timber.d("Loading request token.");
    Token accessToken = service.getAccessToken(tokenHolder.getRequestToken(), new Verifier(verifier));
    Timber.d(String.valueOf(accessToken));
    tokenHolder.setRequestToken(null);
    tokenHolder.setAccessToken(accessToken);
    return data = accessToken;
  }

}
