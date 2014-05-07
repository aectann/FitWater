package io.github.aectann.fitwater.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import io.github.aectann.fitwater.FitWater;

/**
 * Created by aectann on 7/05/14.
 */
public abstract class BaseAsyncTaskLoader<T extends Object> extends AsyncTaskLoader<T> {

  T data;

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
}
