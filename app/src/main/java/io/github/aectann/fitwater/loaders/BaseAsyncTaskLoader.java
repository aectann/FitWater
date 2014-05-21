package io.github.aectann.fitwater.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.google.gson.Gson;

import org.scribe.oauth.OAuthService;

import javax.inject.Inject;

import io.github.aectann.fitwater.CredentialsStore;
import io.github.aectann.fitwater.FitWater;

/**
 * Created by aectann on 7/05/14.
 */
public abstract class BaseAsyncTaskLoader<T> extends AsyncTaskLoader<T> {

  T data;

  @Inject
  OAuthService service;

  @Inject
  CredentialsStore credentialsStore;

  @Inject
  Gson gson;

  public BaseAsyncTaskLoader(Context context) {
    super(context);
    ((FitWater)context.getApplicationContext()).inject(this);
  }

  @Override
  protected void onStartLoading() {
    if (data != null) {
      // If we currently have a result available, deliver it
      // immediately.
      deliverResult(data);
    } else {
      forceLoad();
    }
  }

  @Override
  protected void onStopLoading() {
    super.onStopLoading();
    cancelLoad();
  }

  @Override
  protected void onReset() {
    super.onReset();
    data = null;
  }
}
