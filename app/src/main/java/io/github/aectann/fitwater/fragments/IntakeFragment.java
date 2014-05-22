package io.github.aectann.fitwater.fragments;

import android.app.LoaderManager;
import android.content.Loader;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.github.aectann.fitwater.R;
import io.github.aectann.fitwater.loaders.GoalLoader;
import io.github.aectann.fitwater.loaders.IntakeLoader;
import io.github.aectann.fitwater.model.Goal;
import io.github.aectann.fitwater.model.Intake;

/**
 * Created by aectann on 7/05/14.
 */
public class IntakeFragment extends BaseListFragment implements LoaderManager.LoaderCallbacks {

  private static final int GOAL_LOADER = 0;
  private static final int INTAKE_LOADER = 1;

  private View goalView;
  private Goal goal;
  private Intake intake;
  private IntakeAdapter intakeAdapter;
  private View todaysIntakeHeader;

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ListView listView = getListView();
    listView.setDivider(new ColorDrawable(Color.TRANSPARENT));
    listView.addHeaderView(goalView = createOrBindGoalView(goalView), null, false);
    listView.addHeaderView(todaysIntakeHeader = createOrBindTodaysIntakeHeader(todaysIntakeHeader), null, false);
  }

  private View createOrBindTodaysIntakeHeader(View convertView) {
    if (convertView == null) {
      convertView = getActivity().getLayoutInflater().inflate(R.layout.view_todays_intake_header, getListView(), false);
      IntakeHeaderViewHolder.attach(convertView);
    }
    IntakeHeaderViewHolder viewHolder = (IntakeHeaderViewHolder) convertView.getTag();
    if (intake == null) {
      viewHolder.progress.setVisibility(View.VISIBLE);
      viewHolder.label.setVisibility(View.INVISIBLE);
    } else {
      viewHolder.progress.setVisibility(View.INVISIBLE);
      viewHolder.label.setText(getString(R.string.todays_intake, (int) intake.getSummary().getWater()));
      viewHolder.label.setVisibility(View.VISIBLE);
    }
    return convertView;
  }

  private View createOrBindGoalView(View convertView) {
    if (convertView == null) {
      convertView = getActivity().getLayoutInflater().inflate(R.layout.view_goal, getListView(), false);
      GoalViewHolder.attach(convertView);
    }
    GoalViewHolder viewHolder = (GoalViewHolder) convertView.getTag();
    if (goal == null) {
      viewHolder.progress.setVisibility(View.VISIBLE);
      viewHolder.goalLabel.setText(R.string.goal_loading_label);
      viewHolder.goalValue.setVisibility(View.INVISIBLE);
    } else {
      viewHolder.progress.setVisibility(View.INVISIBLE);
      viewHolder.goalLabel.setText(R.string.goal_label);
      viewHolder.goalValue.setText(getString(R.string.goal_value, goal.getGoal()));
      viewHolder.goalValue.setVisibility(View.VISIBLE);
    }
    return convertView;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    getLoaderManager().initLoader(GOAL_LOADER, getArguments(), this);
    getLoaderManager().initLoader(INTAKE_LOADER, getArguments(), this);
    intakeAdapter = new IntakeAdapter();
    setListAdapter(intakeAdapter);
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
        goal = (Goal) data;
        createOrBindGoalView(goalView);
        break;
      case INTAKE_LOADER:
        intake = (Intake) data;
        createOrBindTodaysIntakeHeader(todaysIntakeHeader);
        intakeAdapter.notifyDataSetChanged();
        break;
    }
  }

  static class GoalViewHolder {

    @InjectView(R.id.goal_label)
    TextView goalLabel;

    @InjectView(R.id.goal_value)
    TextView goalValue;

    @InjectView(R.id.progress)
    View progress;

    public GoalViewHolder(View view) {
      ButterKnife.inject(this, view);
      view.setTag(this);
    }

    public static void attach(View convertView) {
      new GoalViewHolder(convertView);
    }
  }

  private class IntakeAdapter extends BaseAdapter implements ListAdapter {
    @Override
    public int getCount() {
      return 0;
    }

    @Override
    public Object getItem(int position) {
      return null;
    }

    @Override
    public long getItemId(int position) {
      return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      return null;
    }
  }

  static class IntakeHeaderViewHolder {

    @InjectView(R.id.progress)
    View progress;

    @InjectView(R.id.todays_intake_label)
    TextView label;

    public IntakeHeaderViewHolder(View view) {
      ButterKnife.inject(this, view);
      view.setTag(this);
    }

    public static void attach(View view) {
      new IntakeHeaderViewHolder(view);
    }
  }
}
