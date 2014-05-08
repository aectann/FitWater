package io.github.aectann.fitwater.loaders;

import android.content.Context;

import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import io.github.aectann.fitwater.CredentialsStore;

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
  @DebugLog
  public String loadInBackground() {
    Token requestToken = service.getRequestToken();
    tokenHolder.setRequestToken(requestToken);
    return data = service.getAuthorizationUrl(requestToken);
  }

}
