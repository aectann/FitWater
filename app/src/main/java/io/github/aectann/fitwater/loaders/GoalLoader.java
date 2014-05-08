package io.github.aectann.fitwater.loaders;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.util.Date;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import io.github.aectann.fitwater.CredentialsStore;
import io.github.aectann.fitwater.model.Goal;

/**
 * Created by aectann on 7/05/14.
 */
public class GoalLoader extends BaseAsyncTaskLoader<Goal> {

  @Inject
  CredentialsStore credentialsStore;

  @Inject
  OAuthService service;

  public GoalLoader(Context context) {
    super(context);
  }

  @Override
  @DebugLog
  public Goal loadInBackground() {
    OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.fitbit.com/1/user/-/foods/log/water/goal.json");
    service.signRequest(credentialsStore.getAccessToken(), request);
    try {
      String body = request.send().getBody();
      JSONObject goal = new JSONObject(body).getJSONObject("goal");
      Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
      Goal g = gson.fromJson(goal.toString(), Goal.class);
      return data = g;
    } catch (JSONException e) {
      error(e);
      cancelLoad();
    }
    return null;
  }

  @DebugLog
  void error(JSONException e) {
  }

}
