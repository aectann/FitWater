package io.github.aectann.fitwater;

import android.content.Context;
import android.content.SharedPreferences;

import org.scribe.model.Token;

/**
 * Created by aectann on 4/05/14.
 */
public class CredentialsStore {

  private static final String REQUEST_TOKEN = "request-token";
  private static final String SECRET_SUFFIX = "-secret";
  private static final String CREDENTIAL_STORE = "credential-store";
  private static final String ACCESS_TOKEN = "access-token";

  private final Context context;
  private Token requestToken;
  private Token accessToken;

  public CredentialsStore(Context context) {
    this.context = context.getApplicationContext();
  }

  public Token getRequestToken() {
    if (requestToken == null) {
      requestToken = getToken(REQUEST_TOKEN);
    }
    return requestToken;
  }

  private Token getToken(String key) {
    Token token = null;
    SharedPreferences credentialStore = context.getSharedPreferences(CREDENTIAL_STORE, Context.MODE_PRIVATE);
    String rawToken = credentialStore.getString(key, null);
    String tokenSecret = credentialStore.getString(key + SECRET_SUFFIX, null);
    if (rawToken != null) {
      token = new Token(rawToken, tokenSecret);
    }
    return token;
  }

  public void setRequestToken(Token requestToken) {
    this.requestToken = requestToken;
    saveToken(requestToken, REQUEST_TOKEN);
  }

  private void saveToken(Token token, String key) {
    String tokenSecret = key + SECRET_SUFFIX;
    SharedPreferences credentialStore = context.getSharedPreferences(CREDENTIAL_STORE, Context.MODE_PRIVATE);
    if (token != null) {
      credentialStore.edit()
              .putString(key, token.getToken())
              .putString(tokenSecret, token.getSecret())
              .commit();
    } else {
      credentialStore.edit().remove(key).remove(tokenSecret).commit();
    }
  }

  public void setAccessToken(Token accessToken) {
    this.accessToken = accessToken;
    saveToken(accessToken, ACCESS_TOKEN);
  }

  public Token getAccessToken() {
    if (this.accessToken == null) {
      this.accessToken = getToken(ACCESS_TOKEN);
    }
    return this.accessToken;
  }

}
