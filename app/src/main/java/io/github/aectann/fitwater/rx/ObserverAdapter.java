package io.github.aectann.fitwater.rx;

import rx.Observer;

/**
 * Created by aectann on 22/05/14.
 */
public abstract class ObserverAdapter<T> implements Observer<T> {
  @Override
  public void onCompleted() {
  }

  @Override
  public void onError(Throwable throwable) {
  }

  @Override
  public abstract void onNext(T t);
}
