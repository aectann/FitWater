package io.github.aectann.fitwater.loaders;

/**
 * Created by aectann on 22/05/14.
 */
public class RequestResult<T> {

  private T data;

  private String errorMessage;

  public RequestResult(T data) {
    this(data, null);
  }

  public RequestResult(T data, String errorMessage) {
    this.data = data;
    this.errorMessage = errorMessage;
  }

  public RequestResult(String errorMessage) {
    this(null, errorMessage);
  }

  public T getData() {
    return data;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public boolean hasError() {
    return errorMessage != null;
  }

  @Override
  public String toString() {
    return "RequestResult{" +
            "data=" + data +
            ", errorMessage='" + errorMessage + '\'' +
            '}';
  }
}
