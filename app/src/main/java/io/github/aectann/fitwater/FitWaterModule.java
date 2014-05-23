package io.github.aectann.fitwater;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.aectann.fitwater.io.ApiModule;
import io.github.aectann.fitwater.io.UserLoggedIn;

/**
 * Created by aectann on 4/05/14.
 */
@Module(
        injects = {
                    MainActivity.class,
                    LogoutActivity.class,
                    CredentialsStore.class
        },
        includes = {
                ApiModule.class
        }

)
public class FitWaterModule {

  private final FitWater fitwater;

  public FitWaterModule(FitWater fitWater) {
    this.fitwater = fitWater;
  }

  @Provides
  @Singleton
  Application provideApplication() {
    return fitwater;
  }

  @Provides
  @UserLoggedIn
  boolean provideUserLoggedIn(CredentialsStore credentialsStore) {
    return credentialsStore.getAccessToken() != null;
  }
}
