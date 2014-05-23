package io.github.aectann.fitwater;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

/**
 * Created by aectann on 4/05/14.
 */
public class FitBitApi extends DefaultApi10a {

  public static final String FITBIT_API_URL = "http://api.fitbit.com/1";

  @Override
  public String getRequestTokenEndpoint() {
    return "https://api.fitbit.com/oauth/request_token";
  }

  @Override
  public String getAccessTokenEndpoint() {
    return "https://api.fitbit.com/oauth/access_token";
  }

  @Override
  public String getAuthorizationUrl(Token requestToken) {
    return "https://www.fitbit.com/oauth/authorize?oauth_token=" + requestToken.getToken() + "&display=touch";
  }
}
