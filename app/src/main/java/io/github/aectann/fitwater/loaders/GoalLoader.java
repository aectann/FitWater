package io.github.aectann.fitwater.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import javax.inject.Inject;

import io.github.aectann.fitwater.CredentialsStore;
import timber.log.Timber;

/**
 * Created by aectann on 7/05/14.
 */
public class GoalLoader extends BaseAsyncTaskLoader<String> {

  @Inject
  CredentialsStore credentialsStore;

  @Inject
  OAuthService service;

  public GoalLoader(Context context) {
    super(context);
  }

  @Override
  public String loadInBackground() {
    Timber.d("Loading goal..");
    OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.fitbit.com/1/user/-/foods/log/water/goal.json");
    service.signRequest(credentialsStore.getAccessToken(), request);
    return data = request.send().getBody();
  }

}
