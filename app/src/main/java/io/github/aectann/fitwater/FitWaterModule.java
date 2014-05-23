package io.github.aectann.fitwater;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.scribe.builder.ServiceBuilder;
import org.scribe.oauth.OAuthService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.aectann.fitwater.fragments.IntakeFragment;
import io.github.aectann.fitwater.fragments.LoginFragment;
import io.github.aectann.fitwater.fragments.SettingsFragment;
import io.github.aectann.fitwater.io.ApiModule;
import io.github.aectann.fitwater.loaders.AccessTokenLoader;
import io.github.aectann.fitwater.loaders.RequestTokenLoader;

/**
 * Created by aectann on 4/05/14.
 */
@Module(
        injects = {
                    AccessTokenLoader.class,
                    CredentialsStore.class,
                    Gson.class,
                    IntakeFragment.class,
                    LoginFragment.class,
                    LogoutActivity.class,
                    MainActivity.class,
                    OAuthCallbackActivity.class,
                    OAuthService.class,
                    RequestTokenLoader.class,
                    SettingsFragment.class
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
  OAuthService provideOAuthService() {
    return new ServiceBuilder().provider(FitBitApi.class).
            apiKey(BuildConfig.APP_CLIENT_ID).
            apiSecret(BuildConfig.APP_SECRET).
            callback("fitwater://oauth_callback").
            build();
  }

  @Provides
  @Singleton
  Gson provideGson() {
    return new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
  }

  @Provides
  @Singleton
  Application provideApplication() {
    return fitwater;
  }
}
