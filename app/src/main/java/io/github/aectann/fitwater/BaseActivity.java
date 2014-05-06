package io.github.aectann.fitwater;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by aectann on 5/05/14.
 */
public class BaseActivity extends Activity{

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ((FitWater) getApplication()).inject(this);
  }
}
