package io.github.aectann.fitwater.rx;

import hugo.weaving.DebugLog;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;
import timber.log.Timber;

/**
 * Created by aectann on 22/05/14.
 */
public enum GlobalUiEvents {

  REFRESH;

  private PublishSubject<GlobalUiEvents> subject = PublishSubject.create();

  @DebugLog
  public Subscription subscribe(Observer<GlobalUiEvents> observer) {
    return subject.observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
  }

  public void fire() {
    Timber.d(this + " fired!");
    subject.onNext(this);
  }
}
