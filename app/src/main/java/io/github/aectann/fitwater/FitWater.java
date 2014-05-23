package io.github.aectann.fitwater;

import android.app.Application;

import dagger.ObjectGraph;
import dagger.internal.Modules;
import timber.log.Timber;

/**
 * Created by aectann on 4/05/14.
 */
public class FitWater extends Application {

  private ObjectGraph objectGraph;

  @Override
  public void onCreate() {
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    } else {
      // TODO Crashlytics.start(this);
      // TODO Timber.plant(new CrashlyticsTree());
    }
    objectGraph = ObjectGraph.create(new FitWaterModule(this));
    objectGraph.get(CredentialsStore.class);
  }

  public void inject(Object object) {
    objectGraph.inject(object);
  }
}
