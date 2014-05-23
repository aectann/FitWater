package io.github.aectann.fitwater.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.scribe.model.Token;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import io.github.aectann.fitwater.CredentialsStore;
import io.github.aectann.fitwater.R;
import io.github.aectann.fitwater.io.FitbitOAuthService;
import rx.Observer;
import rx.Subscription;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends BaseFragment {

  private static final int LOADER_ID = 0;
  private static final String AUTHORIZE_CLICKED = "authorize-clicked";
  private static final String AUTHORIZATION_URL = "authorization-url";
  private String authorizationUrl;
  private boolean authorizeClicked;

  @Inject
  CredentialsStore credentialsStore;

  @Inject
  FitbitOAuthService fitbitOAuthService;


  @InjectView(R.id.progress)
  View progress;

  private Subscription requestTokenSubscription;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_login, container, false);
      return rootView;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (savedInstanceState != null) {
      authorizeClicked = savedInstanceState.getBoolean(AUTHORIZE_CLICKED);
      authorizationUrl = savedInstanceState.getString(AUTHORIZATION_URL);
    }
    getRequestToken();
  }

  private void getRequestToken() {
    if (requestTokenSubscription != null) {
      return;
    }
    requestTokenSubscription = fitbitOAuthService.getRequestToken(new Observer<Token>() {
      @Override
      public void onCompleted() {
        requestTokenSubscription.unsubscribe();
        requestTokenSubscription = null;
        progress.setVisibility(View.INVISIBLE);
      }

      @Override
      public void onError(Throwable throwable) {
        authorizationUrl = null;
        Toast.makeText(getActivity(), "Failed to connect to Fitbit.com. Check your network connection.", Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onNext(Token token) {
        credentialsStore.setRequestToken(token);
        authorizationUrl = fitbitOAuthService.getAuthorizationUrl(token);
        redirect();
      }
    });
  }

  @Override
  public void onDetach() {
    super.onDetach();
    if (requestTokenSubscription != null) {
      requestTokenSubscription.unsubscribe();
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putBoolean(AUTHORIZE_CLICKED, authorizeClicked);
    outState.putString(AUTHORIZATION_URL, authorizationUrl);
  }

  private void redirect() {
    if (this.authorizationUrl == null) {
      progress.setVisibility(View.VISIBLE);
      getRequestToken();
    } else if (authorizeClicked) {
      Intent intent = new Intent();
      intent.setAction(Intent.ACTION_VIEW);
      intent.setData(Uri.parse(this.authorizationUrl));
      startActivityForResult(intent, LOADER_ID);
    }
  }

  @OnClick(R.id.fitbit_button)
  public void authorize(View v){
    this.authorizeClicked = true;
    redirect();
  }

}
