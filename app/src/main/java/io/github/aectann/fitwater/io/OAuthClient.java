package io.github.aectann.fitwater.io;

import com.squareup.okhttp.OkHttpClient;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import hugo.weaving.DebugLog;
import io.github.aectann.fitwater.CredentialsStore;
import retrofit.client.Header;
import retrofit.client.OkClient;
import retrofit.client.Request;
import retrofit.client.Response;

/**
 * Created by aectann on 22/05/14.
 */
@Singleton
public class OAuthClient extends OkClient {

  OAuthService oAuthService;

  CredentialsStore credentialsStore;

  @Inject
  public OAuthClient(OkHttpClient client, OAuthService oAuthService, CredentialsStore credentialsStore) {
    super(client);
    this.oAuthService = oAuthService;
    this.credentialsStore = credentialsStore;
  }

  @Override
  @DebugLog
  public Response execute(Request request) throws IOException {
    OAuthRequest oAuthRequest = toOAuthRequest(request);
    if (credentialsStore.getAccessToken() != null) {
      oAuthService.signRequest(credentialsStore.getAccessToken(), oAuthRequest);
    }
    request = toRetrofitRequest(request, oAuthRequest);
    return super.execute(request);
  }

  private Request toRetrofitRequest(Request request, OAuthRequest oAuthRequest) {
    List<Header> headerList = new ArrayList<>();
    Map<String, String> oAuthHeaders = oAuthRequest.getHeaders();
    for (String header : oAuthHeaders.keySet()) {
      headerList.add(new Header(header, oAuthHeaders.get(header)));
    }
    request = new Request(request.getMethod(), oAuthRequest.getUrl(), headerList, request.getBody());
    return request;
  }

  private OAuthRequest toOAuthRequest(Request request) {
    OAuthRequest oAuthRequest = new OAuthRequest(Verb.valueOf(request.getMethod()), request.getUrl());
    List<Header> headers = request.getHeaders();
    for (Header h : headers) {
      oAuthRequest.addHeader(h.getName(), h.getValue());
    }
    return oAuthRequest;
  }
}
