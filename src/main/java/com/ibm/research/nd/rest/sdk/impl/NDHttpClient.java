package com.ibm.research.nd.rest.sdk.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

class NDHttpClient implements HttpClient
{
  private HttpClient delegate;
  private boolean    userBound = false;

  NDHttpClient(String apiKey, String contentType, String accept)
  {
    this(apiKey, contentType, accept, null, null);
  }

  NDHttpClient(String apiKey, String contentType, String accept, String adminKey)
  {
    this(apiKey, contentType, accept, null, adminKey);
  }

  NDHttpClient(String apiKey, String contentType, String accept, String userToken, String adminKey)
  {
    HttpClientBuilder clientBuilder = HttpClientBuilder.create();
    Set<Header> headers = new HashSet<Header>();
    headers.add(new BasicHeader("Accept", accept));
    headers.add(new BasicHeader("Content-Type", contentType));
    headers.add(new BasicHeader("api_key", apiKey));
    if (userToken != null)
    {
      headers.add(new BasicHeader("user_token", DatatypeConverter.printBase64Binary(userToken.getBytes())));
      this.userBound = true;
    }
    if (adminKey != null)
    {
      headers.add(new BasicHeader("admin_key", adminKey));
    }
    clientBuilder.setDefaultHeaders(headers);
    this.delegate = clientBuilder.build();
  }

  @Override
  public HttpParams getParams()
  {
    return this.delegate.getParams();
  }

  @Override
  public ClientConnectionManager getConnectionManager()
  {
    return this.delegate.getConnectionManager();
  }

  @Override
  public HttpResponse execute(HttpUriRequest request) throws IOException, ClientProtocolException
  {
    return this.delegate.execute(request);
  }

  @Override
  public HttpResponse execute(HttpUriRequest request, HttpContext context) throws IOException, ClientProtocolException
  {
    return this.delegate.execute(request, context);
  }

  @Override
  public HttpResponse execute(HttpHost target, HttpRequest request) throws IOException, ClientProtocolException
  {
    return this.delegate.execute(target, request);
  }

  @Override
  public HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context)
      throws IOException, ClientProtocolException
  {
    return this.delegate.execute(target, request, context);
  }

  @Override
  public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler)
      throws IOException, ClientProtocolException
  {
    return this.delegate.execute(request, responseHandler);
  }

  @Override
  public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context)
      throws IOException, ClientProtocolException
  {
    return this.delegate.execute(request, responseHandler, context);
  }

  @Override
  public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler)
      throws IOException, ClientProtocolException
  {
    return this.delegate.execute(target, request, responseHandler);
  }

  @Override
  public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler,
      HttpContext context) throws IOException, ClientProtocolException
  {
    return this.delegate.execute(target, request, responseHandler, context);
  }

  @Override
  public String toString()
  {
    return this.userBound ? "user bound API client" : "unbound API client";
  }
}
