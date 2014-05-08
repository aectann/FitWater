package io.github.aectann.fitwater.loaders;

import android.content.Context;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;

import java.text.SimpleDateFormat;
import java.util.Date;

import hugo.weaving.DebugLog;
import io.github.aectann.fitwater.model.Intake;

/**
 * Created by aectann on 7/05/14.
 */
public class IntakeLoader extends BaseAsyncTaskLoader<Intake> {

  public IntakeLoader(Context context) {
    super(context);
  }

  @Override
  @DebugLog
  public Intake loadInBackground() {
    String dateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    OAuthRequest request = new OAuthRequest(Verb.GET, String.format("http://api.fitbit.com/1/user/-/foods/log/water/date/%s.json", dateString));
    service.signRequest(credentialsStore.getAccessToken(), request);
    Intake intake = gson.fromJson(request.send().getBody(), Intake.class);
    return data = intake;
  }
}
