package io.github.aectann.fitwater.loaders;

import android.content.Context;

import org.scribe.model.Token;
import org.scribe.model.Verifier;

import hugo.weaving.DebugLog;

/**
 * Created by aectann on 7/05/14.
 */
public class AccessTokenLoader extends BaseAsyncTaskLoader<Token> {

  private final String verifier;

  @DebugLog
  public AccessTokenLoader(Context context, String verifier) {
    super(context);
    this.verifier = verifier;
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
