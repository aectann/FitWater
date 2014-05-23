package io.github.aectann.fitwater;

import android.content.Intent;
import android.os.Bundle;

import org.scribe.model.Token;

import javax.inject.Inject;

import io.github.aectann.fitwater.io.FitbitOAuthService;
import rx.Observer;
import rx.Subscription;
import timber.log.Timber;

/**
 * Created by aectann on 4/05/14.
 */
public class OAuthCallbackActivity extends BaseActivity {

  @Inject
  FitbitOAuthService fitbitOAuthService;

  @Inject
  CredentialsStore credentialsStore;

  private Subscription accessTokenSubscription;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_oauth_callback);
    String oauthVerifier = getIntent().getData().getQueryParameter("oauth_verifier");
    accessTokenSubscription = fitbitOAuthService.getAccessToken(oauthVerifier, new Observer<Token>() {
      @Override
      public void onCompleted() {
        startActivity(new Intent(OAuthCallbackActivity.this, MainActivity.class));
        finish();
      }

      @Override
      public void onError(Throwable throwable) {
        Timber.e(throwable, "Failed to fetch access token.");
      }

      @Override
      public void onNext(Token token) {
        credentialsStore.setAccessToken(token);
        credentialsStore.setRequestToken(null);
      }
    });
  }

  @Override
  public void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    if (accessTokenSubscription != null) {
      accessTokenSubscription.unsubscribe();
    }
  }
}
