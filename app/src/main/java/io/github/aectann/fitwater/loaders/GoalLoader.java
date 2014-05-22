package io.github.aectann.fitwater.loaders;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;

import hugo.weaving.DebugLog;
import io.github.aectann.fitwater.model.Goal;
import timber.log.Timber;

/**
 * Created by aectann on 7/05/14.
 */
public class GoalLoader extends BaseAsyncTaskLoader<Goal> {
  public GoalLoader(Context context) {
    super(context);
  }

  @Override
  @DebugLog
  public RequestResult<Goal> loadInBackground() {
    OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.fitbit.com/1/user/-/foods/log/water/goal.json");
    service.signRequest(credentialsStore.getAccessToken(), request);
    try {
      Response response = request.send();
      String body = response.getBody();
      JSONObject goal = new JSONObject(body).getJSONObject("goal");
      Goal g = gson.fromJson(goal.toString(), Goal.class);
      data = new RequestResult<>(g);
      return data;
    } catch (Exception e) {
      Timber.e(e, "Failed to parse response.");
      return new RequestResult<>("Failed to load goal.");
    }
  }

}
