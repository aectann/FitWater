package io.github.aectann.fitwater.loaders;

import android.content.Context;

import org.scribe.model.Token;

import hugo.weaving.DebugLog;

/**
 * Created by aectann on 7/05/14.
 */
public class RequestTokenLoader extends BaseAsyncTaskLoader<String> {

  public RequestTokenLoader(Context context) {
    super(context);
  }

  @Override
  @DebugLog
  public RequestResult<String> loadInBackground() {
    try {
      Token requestToken = service.getRequestToken();
      credentialsStore.setRequestToken(requestToken);
      return data = new RequestResult<>(service.getAuthorizationUrl(requestToken), null);
    } catch (Exception e) {
      return new RequestResult<>(null, "Failed to load request token.");
    }
  }

}
