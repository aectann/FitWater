package io.github.aectann.fitwater;

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
import io.github.aectann.fitwater.loaders.AccessTokenLoader;
import io.github.aectann.fitwater.loaders.GoalLoader;
import io.github.aectann.fitwater.loaders.IntakeLoader;
import io.github.aectann.fitwater.loaders.RequestTokenLoader;

/**
 * Created by aectann on 4/05/14.
 */
@Module(
        injects = {
                    AccessTokenLoader.class,
                    CredentialsStore.class,
                    GoalLoader.class,
                    Gson.class,
                    IntakeFragment.class,
                    IntakeLoader.class,
                    LoginFragment.class,
                    LogoutActivity.class,
                    MainActivity.class,
                    OAuthCallbackActivity.class,
                    OAuthService.class,
                    RequestTokenLoader.class,
                    SettingsFragment.class
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
  CredentialsStore provideCredentialStore() {
    return new CredentialsStore(fitwater);
  }

  @Provides
  @Singleton
  Gson provideGson() {
    return new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
  }

}
