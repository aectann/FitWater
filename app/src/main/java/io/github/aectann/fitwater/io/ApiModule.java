package io.github.aectann.fitwater.io;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.HttpResponseCache;
import com.squareup.okhttp.OkHttpClient;

import org.scribe.builder.ServiceBuilder;
import org.scribe.oauth.OAuthService;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.aectann.fitwater.BuildConfig;
import io.github.aectann.fitwater.OAuthCallbackActivity;
import io.github.aectann.fitwater.fragments.IntakeFragment;
import io.github.aectann.fitwater.fragments.LoginFragment;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import timber.log.Timber;

@Module(
    injects = {
            OAuthClient.class,
            OAuthCallbackActivity.class,
            LoginFragment.class,
            IntakeFragment.class
    },
    complete = false,
    library = true
)
public final class ApiModule {

  static final int DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

  @Provides @Singleton
  Endpoint provideEndpoint() {
    return Endpoints.newFixedEndpoint(FitBitApi.FITBIT_API_URL);
  }

  @Provides @Singleton
  RestAdapter provideRestAdapter(Endpoint endpoint, OAuthClient client, Gson gson) {
    return new RestAdapter.Builder() //
        .setClient(client) //
        .setEndpoint(endpoint)
        .setConverter(new GsonConverter(gson))
        .build();
  }

  @Provides @Singleton FitbitService provideFitbitService(RestAdapter restAdapter) {
    return restAdapter.create(FitbitService.class);
  }

  @Provides @Singleton OkHttpClient provideOkHttpClient(Application app) {
    return createOkHttpClient(app);
  }

  static OkHttpClient createOkHttpClient(Application app) {
    OkHttpClient client = new OkHttpClient();

    // Install an HTTP cache in the application cache directory.
    try {
      File cacheDir = new File(app.getCacheDir(), "http");
      HttpResponseCache cache = new HttpResponseCache(cacheDir, DISK_CACHE_SIZE);
      client.setResponseCache(cache);
    } catch (IOException e) {
      Timber.e(e, "Unable to install disk cache.");
    }
    return client;
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
}
