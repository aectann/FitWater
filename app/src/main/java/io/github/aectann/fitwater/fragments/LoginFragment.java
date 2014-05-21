package io.github.aectann.fitwater.fragments;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import io.github.aectann.fitwater.CredentialsStore;
import io.github.aectann.fitwater.R;
import io.github.aectann.fitwater.loaders.RequestTokenLoader;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<String> {

  private static final int LOADER_ID = 0;
  private static final String AUTHORIZE_CLICKED = "authorize-clicked";
  private static final String AUTHORIZATION_URL = "authorization-url";
  private String authorizationUrl;
  private boolean authorizeClicked;

  @Inject
  CredentialsStore credentialsStore;

  @InjectView(R.id.progress)
  View progress;

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
    getLoaderManager().initLoader(LOADER_ID, getArguments(), this);
  }

  @Override
  public void onResume() {
    super.onResume();
    getLoaderManager().getLoader(LOADER_ID).reset();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putBoolean(AUTHORIZE_CLICKED, authorizeClicked);
    outState.putString(AUTHORIZATION_URL, authorizationUrl);
  }

  @Override
  public Loader<String> onCreateLoader(int id, Bundle args) {
    return new RequestTokenLoader(getActivity().getApplicationContext());
  }

  @Override
  public void onLoadFinished(Loader<String> loader, String authorizationUrl) {
    this.authorizationUrl = authorizationUrl;
    progress.setVisibility(View.INVISIBLE);
    redirect();
  }

  private void redirect() {
    if (this.authorizationUrl == null) {
      Loader<Object> loader = getLoaderManager().getLoader(LOADER_ID);
      if (!loader.isStarted()) {
        loader.forceLoad();
      }
      progress.setVisibility(View.VISIBLE);
    } else if (authorizeClicked) {
      Intent intent = new Intent();
      intent.setAction(Intent.ACTION_VIEW);
      intent.setData(Uri.parse(this.authorizationUrl));
      startActivityForResult(intent, LOADER_ID);
    }
  }

  @Override
  public void onLoaderReset(Loader<String> loader) {
    this.authorizationUrl = null;
    this.authorizeClicked = false;
    this.credentialsStore.setAccessToken(null);
  }

  @OnClick(R.id.fitbit_button)
  public void authorize(View v){
    this.authorizeClicked = true;
    redirect();
  }

}
