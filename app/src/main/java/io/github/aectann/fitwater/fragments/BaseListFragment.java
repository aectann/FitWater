package io.github.aectann.fitwater.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import io.github.aectann.fitwater.FitWater;

/**
 * Created by aectann on 7/05/14.
 */
public class BaseListFragment extends ListFragment {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ((FitWater)getActivity().getApplication()).inject(this);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);
  }

  @Override
  public void onDestroyView() {
    ButterKnife.reset(this);
    super.onDestroyView();
  }
}
