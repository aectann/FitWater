package io.github.aectann.fitwater.fragments;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.InjectView;
import io.github.aectann.fitwater.R;
import io.github.aectann.fitwater.loaders.GoalLoader;
import io.github.aectann.fitwater.loaders.IntakeLoader;
import io.github.aectann.fitwater.model.Goal;

/**
 * Created by aectann on 7/05/14.
 */
public class IntakeFragment extends BaseFragment implements LoaderManager.LoaderCallbacks {

  private static final int GOAL_LOADER = 0;
  private static final int INTAKE_LOADER = 1;

  @InjectView(R.id.goal)
  TextView goal;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_intake, container, false);
    return rootView;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    getLoaderManager().initLoader(GOAL_LOADER, getArguments(), this);
    getLoaderManager().initLoader(INTAKE_LOADER, getArguments(), this);
  }

  @Override
  public Loader onCreateLoader(int id, Bundle args) {
    switch (id) {
      case GOAL_LOADER:
        return new GoalLoader(getActivity());
      case INTAKE_LOADER:
        return new IntakeLoader(getActivity());
      default:
        return null;
    }
  }

  @Override
  public void onLoaderReset(Loader loader) {

  }

  @Override
  public void onLoadFinished(Loader loader, Object data) {
    switch (loader.getId()) {
      case GOAL_LOADER:
        Goal g = (Goal) data;
        String goalString = String.valueOf(g.getGoal());
        goal.setText(goalString);
        break;
    }
  }
}
