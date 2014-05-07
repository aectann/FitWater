package io.github.aectann.fitwater.fragments;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.github.aectann.fitwater.CredentialsStore;
import io.github.aectann.fitwater.R;
import io.github.aectann.fitwater.loaders.GoalLoader;
import timber.log.Timber;

/**
 * Created by aectann on 7/05/14.
 */
public class IntakeFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<String> {

  @InjectView(R.id.goal)
  TextView goal;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_intake, container, false);
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
    return new GoalLoader(getActivity());
  }

  @Override
  public void onLoadFinished(Loader<String> loader, String data) {
    goal.setText(data);
  }

  @Override
  public void onLoaderReset(Loader<String> loader) {

  }
}
