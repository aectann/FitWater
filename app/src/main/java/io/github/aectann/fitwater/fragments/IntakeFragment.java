package io.github.aectann.fitwater.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import hugo.weaving.DebugLog;
import io.github.aectann.fitwater.R;
import io.github.aectann.fitwater.io.FitbitService;
import io.github.aectann.fitwater.model.Goal;
import io.github.aectann.fitwater.model.GoalResponse;
import io.github.aectann.fitwater.model.Intake;
import io.github.aectann.fitwater.rx.GlobalUiEvents;
import io.github.aectann.fitwater.rx.ObserverAdapter;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by aectann on 7/05/14.
 */
public class IntakeFragment extends BaseListFragment {

  private static final int GOAL_LOADER = 0;
  private static final int INTAKE_LOADER = 1;

  private View goalView;
  private Goal goal;
  private Intake intake;
  private IntakeAdapter intakeAdapter;
  private View todaysIntakeHeader;
  private Subscription refreshSubscription;
  private String errorMessage;

  @Inject
  FitbitService fitbitService;

  private Subscription goalSubscription;
  private Subscription intakeSubscription;

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ListView listView = getListView();
    listView.setDivider(new ColorDrawable(Color.TRANSPARENT));
    listView.addHeaderView(goalView = createOrBindGoalView(goalView), null, false);
    listView.addHeaderView(todaysIntakeHeader = createOrBindTodaysIntakeHeader(todaysIntakeHeader), null, false);
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    refreshSubscription = GlobalUiEvents.REFRESH.subscribe(new ObserverAdapter<GlobalUiEvents>() {
      @Override
      @DebugLog
      public void onNext(GlobalUiEvents globalUiEvents) {
        errorMessage = null;
        if (goalSubscription == null) {
          goalSubscription = fetchGoal();
        }
        if (intakeSubscription == null) {
          intakeSubscription = fetchIntake();
        }
        refreshViews(GOAL_LOADER, null);
        refreshViews(INTAKE_LOADER, null);
      }
    });
  }

  @Override
  public void onDetach() {
    refreshSubscription.unsubscribe();
    goalSubscription.unsubscribe();
    super.onDetach();
  }

  private View createOrBindTodaysIntakeHeader(View convertView) {
    if (convertView == null) {
      convertView = getActivity().getLayoutInflater().inflate(R.layout.view_todays_intake_header, getListView(), false);
      IntakeHeaderViewHolder.attach(convertView);
    }
    IntakeHeaderViewHolder viewHolder = (IntakeHeaderViewHolder) convertView.getTag();
    if (errorMessage != null) {
      viewHolder.progress.setVisibility(View.INVISIBLE);
    } else {
      if (intake == null) {
        viewHolder.progress.setVisibility(View.VISIBLE);
        viewHolder.label.setVisibility(View.INVISIBLE);
      } else {
        viewHolder.progress.setVisibility(View.INVISIBLE);
        viewHolder.label.setText(getString(R.string.todays_intake, (int) intake.getSummary().getWater()));
        viewHolder.label.setVisibility(View.VISIBLE);
      }
    }
    return convertView;
  }

  private View createOrBindGoalView(View convertView) {
    if (convertView == null) {
      convertView = getActivity().getLayoutInflater().inflate(R.layout.view_goal, getListView(), false);
      GoalViewHolder.attach(convertView);
    }
    GoalViewHolder viewHolder = (GoalViewHolder) convertView.getTag();

    if (errorMessage != null) {
      viewHolder.progress.setVisibility(View.INVISIBLE);
      viewHolder.goalLabel.setText(errorMessage);
    } else {
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
    }
    return convertView;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    intakeAdapter = new IntakeAdapter();
    setListAdapter(intakeAdapter);
    goalSubscription = fetchGoal();
    intakeSubscription = fetchIntake();
  }

  private Subscription fetchIntake() {
    String dateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    return fitbitService.getIntake(dateString).observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Intake>() {
      @Override
      public void onCompleted() {
        unsubscribe();
      }

      private void unsubscribe() {
        intakeSubscription.unsubscribe();
        intakeSubscription = null;
      }

      @Override
      public void onError(Throwable throwable) {
        errorMessage = "Failed to load intake.";
        intake = null;
        createOrBindTodaysIntakeHeader(todaysIntakeHeader);
        unsubscribe();
      }

      @Override
      public void onNext(Intake intake) {
        IntakeFragment.this.intake = intake;
        createOrBindTodaysIntakeHeader(todaysIntakeHeader);
      }
    });
  }

  private Subscription fetchGoal() {
    return fitbitService.getGoal().observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<GoalResponse>() {
      @Override
      public void onCompleted() {
        unsubscribe();
      }

      private void unsubscribe() {
        goalSubscription.unsubscribe();
        goalSubscription = null;
      }

      @Override
      public void onError(Throwable throwable) {
        errorMessage = "Failed to load goal.";
        goal = null;
        createOrBindGoalView(goalView);
        unsubscribe();
      }

      @Override
      public void onNext(GoalResponse goalResponse) {
        goal = goalResponse.getGoal();
        createOrBindGoalView(goalView);
      }
    });
  }

  private void refreshViews(int loader, Object data) {
    switch (loader) {
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
