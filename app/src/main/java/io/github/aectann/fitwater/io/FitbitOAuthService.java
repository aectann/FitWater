package io.github.aectann.fitwater.io;

import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.github.aectann.fitwater.CredentialsStore;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by aectann on 23/05/14.
 */
@Singleton
public class FitbitOAuthService {

  private final OAuthService oAuthService;

  private final CredentialsStore credentialsStore;

  @Inject
  public FitbitOAuthService(OAuthService oAuthService, CredentialsStore credentialsStore) {
    this.oAuthService = oAuthService;
    this.credentialsStore = credentialsStore;
  }

  public Subscription getAccessToken(final String verifier, Observer<Token> observer) {
    Subscription subscription = Observable.create(new Observable.OnSubscribe<Token>() {
      @Override
      public void call(Subscriber<? super Token> subscriber) {
        try {
          Token accessToken = oAuthService.getAccessToken(credentialsStore.getRequestToken(), new Verifier(verifier));
          subscriber.onNext(accessToken);
        } catch (Exception e) {
          subscriber.onError(e);
        }
        subscriber.onCompleted();
      }
    }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer);
    return subscription;
  }

  public Subscription getRequestToken(Observer<Token> observer) {
    Subscription subscription = Observable.create(new Observable.OnSubscribe<Token>() {
      @Override
      public void call(Subscriber<? super Token> subscriber) {
        try {
          Token requestToken = oAuthService.getRequestToken();
          subscriber.onNext(requestToken);
        } catch (Exception e) {
          subscriber.onError(e);
        }
        subscriber.onCompleted();
      }
    }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer);
    return subscription;
  }

  public String getAuthorizationUrl(Token token) {
    return oAuthService.getAuthorizationUrl(token);
  }
}
