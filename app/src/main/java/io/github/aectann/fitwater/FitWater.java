package io.github.aectann.fitwater;

import android.app.Application;

import dagger.ObjectGraph;
import timber.log.Timber;

/**
 * Created by aectann on 4/05/14.
 */
public class FitWater extends Application {

  private ObjectGraph objectGraph;

  @Override
  public void onCreate() {
    objectGraph = ObjectGraph.create(new FitWaterModule(this));
    objectGraph.get(CredentialsStore.class);
    Timber.plant(new Timber.DebugTree());
  }

  public void inject(Object object) {
    objectGraph.inject(object);
  }
}
