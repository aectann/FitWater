package io.github.aectann.fitwater.io;

import android.app.Application;
import android.net.Uri;

import com.google.gson.Gson;
import com.squareup.okhttp.HttpResponseCache;
import com.squareup.okhttp.OkHttpClient;

import org.scribe.oauth.OAuthService;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.aectann.fitwater.CredentialsStore;
import io.github.aectann.fitwater.FitBitApi;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.converter.GsonConverter;
import timber.log.Timber;

@Module(
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
  Client provideClient(OkHttpClient client, OAuthService oAuthService, CredentialsStore credentialsStore) {
    return new OAuthClient(client, oAuthService, credentialsStore);
  }

  @Provides @Singleton
  RestAdapter provideRestAdapter(Endpoint endpoint, Client client, Gson gson) {
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
}
