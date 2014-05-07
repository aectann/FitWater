package io.github.aectann.fitwater.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import javax.inject.Inject;

import io.github.aectann.fitwater.CredentialsStore;
import timber.log.Timber;

/**
 * Created by aectann on 7/05/14.
 */
public class RequestTokenLoader extends BaseAsyncTaskLoader<String> {

  @Inject
  OAuthService service;

  @Inject
  CredentialsStore tokenHolder;

  public RequestTokenLoader(Context context) {
    super(context);
  }

  @Override
  public String loadInBackground() {
    Timber.d("Loading request token.");
    Token requestToken = service.getRequestToken();
    Timber.d(String.valueOf(requestToken));
    tokenHolder.setRequestToken(requestToken);
    return data = service.getAuthorizationUrl(requestToken);
  }

}
