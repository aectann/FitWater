package io.github.aectann.fitwater;

import org.scribe.builder.ServiceBuilder;
import org.scribe.oauth.OAuthService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aectann on 4/05/14.
 */
@Module(
        injects = {MainActivity.class, OAuthCallbackActivity.class, RequestTokenHolder.class, OAuthService.class}
)
public class FitWaterModule {

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
  RequestTokenHolder provideRequestTokenHolder() {
    return new RequestTokenHolder();
  }
}
