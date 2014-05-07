package io.github.aectann.fitwater.fragments;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import io.github.aectann.fitwater.R;
import io.github.aectann.fitwater.loaders.RequestTokenLoader;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<String> {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_login, container, false);
      ButterKnife.inject(this, rootView);
      return rootView;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    getLoaderManager().initLoader(0, getArguments(), this);
  }

  @Override
  public Loader<String> onCreateLoader(int id, Bundle args) {
    return new RequestTokenLoader(getActivity().getApplicationContext());
  }

  @Override
  public void onLoadFinished(Loader<String> loader, String authorizationUrl) {
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_VIEW);
    intent.setData(Uri.parse(authorizationUrl));
    startActivityForResult(intent, 0);
  }

  @Override
  public void onLoaderReset(Loader<String> loader) {
  }

}
