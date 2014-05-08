package io.github.aectann.fitwater.loaders;

import android.content.Context;

import org.scribe.oauth.OAuthService;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import io.github.aectann.fitwater.CredentialsStore;
import io.github.aectann.fitwater.model.Intake;

/**
 * Created by aectann on 7/05/14.
 */
public class IntakeLoader extends BaseAsyncTaskLoader<Intake> {

  @Inject
  CredentialsStore credentialsStore;

  @Inject
  OAuthService service;

  public IntakeLoader(Context context) {
    super(context);
  }

  @Override
  @DebugLog
  public Intake loadInBackground() {
    //TODO make request..
    return data = new Intake();
  }
}
