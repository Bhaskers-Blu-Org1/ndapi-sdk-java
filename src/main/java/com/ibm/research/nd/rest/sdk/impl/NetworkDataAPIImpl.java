package com.ibm.research.nd.rest.sdk.impl;

import java.io.EOFException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.research.nd.rest.sdk.api.NDAPIError;
import com.ibm.research.nd.rest.sdk.api.NDAPIException;
import com.ibm.research.nd.rest.sdk.api.NetworkDataAPI;
import com.ibm.research.nd.rest.sdk.api.objects.BillingItem;
import com.ibm.research.nd.rest.sdk.api.objects.Clustering;
import com.ibm.research.nd.rest.sdk.api.objects.ExceptionInfo;
import com.ibm.research.nd.rest.sdk.api.objects.Layout;
import com.ibm.research.nd.rest.sdk.api.objects.SingleValue;
import com.ibm.research.nd.rest.sdk.api.objects.Tree;
import com.ibm.research.nd.rest.sdk.api.objects.TreeLayoutStyle;
import com.ibm.research.nd.rest.sdk.api.objects.User;
import com.ibm.research.nd.rest.sdk.api.objects.ValueType;

/**
 * Implementation of Network Data API alpha
 *
 *
 * @author Daniel K. Weidele
 */
public final class NetworkDataAPIImpl implements NetworkDataAPI
{
  private final Logger LOG = LoggerFactory.getLogger(NetworkDataAPIImpl.class);

  private String       url;
  private String       apiKey;
  private String       adminKey;
  private String       userToken;

  /**
   * Instantiate a client for Network Data API. Call this constructor when executing directly within
   * a Bluemix environment, where Network Data API is bound to your application.
   */
  public NetworkDataAPIImpl()
  {

  }

  /**
   * Create a client for Network Data API.
   *
   * @param url The url of the service (see VCAP_SERVICES in Bluemix).
   * @param apiKey Your API key for service consumption (see VCAP_SERVICES in Bluemix).
   */
  public NetworkDataAPIImpl(String url, String apiKey)
  {
    this.LOG.trace("START: NetworkDataAPI(url={}, apiKey={})", url, apiKey);
    this.url = url;
    this.apiKey = DatatypeConverter.printBase64Binary(apiKey.getBytes());
    this.LOG.trace("END: NetworkDataAPI");
  }

  /**
   * Create a client for Network Data API.
   *
   * @param url The url of the service (see VCAP_SERVICES in Bluemix).
   * @param apiKey Your API key for service consumption (see VCAP_SERVICES in Bluemix).
   * @param adminKey Your Admin key for service consumption (see VCAP_SERVICES in Bluemix).
   */
  public NetworkDataAPIImpl(String url, String apiKey, String adminKey)
  {
    this.LOG.trace("START: NetworkDataAPI(url={}, apiKey={}, adminKey={})", url, apiKey, adminKey);
    if (url == null)
    {
      throw new RuntimeException("Network Data API URL not set. Please see VCAP_SERVICES in Bluemix.");
    }
    this.url = url;
    if (apiKey == null)
    {
      throw new RuntimeException("API Key not set. Please see VCAP_SERVICES in Bluemix.");
    }
    this.apiKey = DatatypeConverter.printBase64Binary(apiKey.getBytes());
    if (adminKey != null)
    {
      this.adminKey = DatatypeConverter.printBase64Binary(adminKey.getBytes());
    }
    this.LOG.trace("END: NetworkDataAPI");
  }

  @Override
  public final boolean ping()
  {
    try
    {
      this.LOG.trace("START: ping()");
      HttpClient client = this.getClient("application/json", "application/json");
      HttpGet get = new HttpGet(this.url + "/ping");
      boolean result = this.execute(client, get);
      this.LOG.trace("END: ping");
      return result;
    }
    catch (NDAPIException e)
    {
      throw e;
    }
  }

  @Override
  public final void login(String email, String password)
  {

    try
    {
      this.LOG.trace("START: login(email={}, password={})", email, "***HIDDEN***");
      HttpClient client = this.getClient("application/json", "application/json");
      HttpPost post = new HttpPost(this.url + "/access/login");
      User user = new User();
      user.setEmail(email);
      user.setPassword(password);
      post.setEntity(this.toJSON(user));
      this.userToken = String.valueOf(this.execute(client, post, SingleValue.class).getValue());
      this.LOG.trace("END: login");
    }
    catch (NDAPIException e)
    {
      throw e;
    }
  }

  @Override
  public void sendResetPasswordCode(String email)
  {
    try
    {
      this.LOG.trace("START: sendResetPasswordCode(email={})", email);
      HttpClient client = this.getClient("application/json", "application/json");
      HttpGet get =
          new HttpGet(this.url + "/access/sendpwcode/" + DatatypeConverter.printBase64Binary(email.getBytes()));
      this.execute(client, get);
      this.LOG.trace("END: sendResetPasswordCode");
    }
    catch (NDAPIException e)
    {
      throw e;
    }
  }

  @Override
  public void resetPassword(String email, String code, String newPassword)
  {
    try
    {
      this.LOG.trace("START: sendResetPasswordCode(email={}, code={}, newPassword={})", email, code, newPassword);
      HttpClient client = this.getClient("application/json", "application/json");
      URIBuilder uri =
          new URIBuilder(this.url + "/access/resetpw/" + DatatypeConverter.printBase64Binary(email.getBytes()));
      uri.addParameter("code", code);
      uri.addParameter("newpassword", newPassword);
      HttpGet get = new HttpGet(uri.build());
      this.execute(client, get);
      this.LOG.trace("END: sendResetPasswordCode");
    }
    catch (NDAPIException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  @Override
  public final void register(User user)
  {
    try
    {
      this.LOG.trace("START: register(user={})", user);
      HttpClient client = this.getClient("application/json", "application/json");
      HttpPost post = new HttpPost(this.url + "/access/register");
      post.setEntity(this.toJSON(user));
      this.userToken = String.valueOf(this.execute(client, post, SingleValue.class).getValue());
      this.LOG.trace("END: register");
    }
    catch (NDAPIException e)
    {
      throw e;
    }
  }

  @Override
  public final void limitUser(String email, BillingItem limit)
  {
    try
    {
      this.LOG.trace("START: limitUser(email={}, limit={})", email, limit);
      HttpClient client = this.getClient("application/json", "application/json");
      HttpPost post = new HttpPost(this.url + "/access/limit/" + DatatypeConverter.printBase64Binary(email.getBytes()));
      post.setEntity(this.toJSON(limit));
      this.execute(client, post);
      this.LOG.trace("END: register");
    }
    catch (NDAPIException e)
    {
      throw e;
    }
  }

  @Override
  public final void limitDefault(BillingItem limit)
  {
    try
    {
      this.LOG.trace("START: limitDefault(limit={})", limit);
      HttpClient client = this.getClient("application/json", "application/json");
      HttpPost post = new HttpPost(this.url + "/access/limit/");
      post.setEntity(this.toJSON(limit));
      this.execute(client, post);
      this.LOG.trace("END: register");
    }
    catch (NDAPIException e)
    {
      throw e;
    }
  }

  @Override
  public final Layout layoutLinkList(String id, String name, String description, HttpEntity linklist,
      Integer dimensions, Double distanceFactor, Boolean adaptNeighborhoodSimilarity, Integer firstEndpointColumnIndex,
      Integer secondEndpointColumnIndex, Boolean valued, Integer valueColumnIndex, ValueType valueType,
      String separator, String quoteChar, String escapeChar, Integer skipLines, Boolean strictQuotes,
      Boolean ignoreLeadingWhiteSpace, Boolean keepCR, Boolean ignoreSelfLoops, Boolean ignoreDuplicateLinks)
  {
    try
    {
      this.LOG.trace("START: layoutLinkList(TODO PARAMETERS)");
      HttpClient client = this.getClient("text/plain", "application/json");
      URIBuilder uri = new URIBuilder(this.url + "/layout/linklist");
      uri.addParameter("id", id);
      uri.addParameter("name", name);
      uri.addParameter("description", description);
      if (dimensions != null)
      {
        uri.addParameter("dimensions", String.valueOf(dimensions));
      }
      if (distanceFactor != null)
      {
        uri.addParameter("distance_factor", String.valueOf(distanceFactor));
      }
      if (adaptNeighborhoodSimilarity != null)
      {
        uri.addParameter("adapt_neighborhood_similarity", String.valueOf(adaptNeighborhoodSimilarity));
      }
      if (firstEndpointColumnIndex != null)
      {
        uri.addParameter("first_endpoint_column_index", String.valueOf(firstEndpointColumnIndex));
      }
      if (secondEndpointColumnIndex != null)
      {
        uri.addParameter("second_endpoint_column_index", String.valueOf(secondEndpointColumnIndex));
      }
      if (valued != null)
      {
        uri.addParameter("valued", String.valueOf(valued));
      }
      if (valueColumnIndex != null)
      {
        uri.addParameter("value_column_index", String.valueOf(valueColumnIndex));
      }
      if (valueType != null)
      {
        uri.addParameter("value_type", valueType.name());
      }
      if (separator != null)
      {
        uri.addParameter("separator", separator);
      }
      if (quoteChar != null)
      {
        uri.addParameter("quote_char", quoteChar);
      }
      if (escapeChar != null)
      {
        uri.addParameter("escape_char", escapeChar);
      }
      if (skipLines != null)
      {
        uri.addParameter("skip_lines", String.valueOf(skipLines));
      }
      if (strictQuotes != null)
      {
        uri.addParameter("strict_quotes", String.valueOf(strictQuotes));
      }
      if (ignoreLeadingWhiteSpace != null)
      {
        uri.addParameter("ignore_leading_white_space", String.valueOf(ignoreLeadingWhiteSpace));
      }
      if (keepCR != null)
      {
        uri.addParameter("keep_cr", String.valueOf(keepCR));
      }
      if (ignoreSelfLoops != null)
      {
        uri.addParameter("ignore_self_loops", String.valueOf(ignoreSelfLoops));
      }
      if (ignoreDuplicateLinks != null)
      {
        uri.addParameter("ignore_duplicate_links", String.valueOf(ignoreDuplicateLinks));
      }
      HttpPost post = new HttpPost(uri.build());
      post.setEntity(linklist);
      Layout result = this.execute(client, post, Layout.class);
      this.LOG.trace("END: layoutLinkList");
      return result;
    }
    catch (NDAPIException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  @Override
  public final Clustering clusterLinkList(String id, String name, String description, HttpEntity linklist,
      Integer firstEndpointColumnIndex, Integer secondEndpointColumnIndex, Boolean valued, Integer valueColumnIndex,
      ValueType valueType, String separator, String quoteChar, String escapeChar, Integer skipLines,
      Boolean strictQuotes, Boolean ignoreLeadingWhiteSpace, Boolean keepCR, Boolean ignoreSelfLoops,
      Boolean ignoreDuplicateLinks, Integer level)
  {
    try
    {
      this.LOG.trace("START: clusterLinkList(TODO PARAMETERS)");
      HttpClient client = this.getClient("text/plain", "application/json");
      URIBuilder uri = new URIBuilder(this.url + "/cluster/linklist");
      uri.addParameter("id", id);
      uri.addParameter("name", name);
      uri.addParameter("description", description);
      if (firstEndpointColumnIndex != null)
      {
        uri.addParameter("first_endpoint_column_index", String.valueOf(firstEndpointColumnIndex));
      }
      if (secondEndpointColumnIndex != null)
      {
        uri.addParameter("second_endpoint_column_index", String.valueOf(secondEndpointColumnIndex));
      }
      if (valued != null)
      {
        uri.addParameter("valued", String.valueOf(valued));
      }
      if (valueColumnIndex != null)
      {
        uri.addParameter("value_column_index", String.valueOf(valueColumnIndex));
      }
      if (valueType != null)
      {
        uri.addParameter("value_type", valueType.name());
      }
      if (separator != null)
      {
        uri.addParameter("separator", separator);
      }
      if (quoteChar != null)
      {
        uri.addParameter("quote_char", quoteChar);
      }
      if (escapeChar != null)
      {
        uri.addParameter("escape_char", escapeChar);
      }
      if (skipLines != null)
      {
        uri.addParameter("skip_lines", String.valueOf(skipLines));
      }
      if (strictQuotes != null)
      {
        uri.addParameter("strict_quotes", String.valueOf(strictQuotes));
      }
      if (ignoreLeadingWhiteSpace != null)
      {
        uri.addParameter("ignore_leading_white_space", String.valueOf(ignoreLeadingWhiteSpace));
      }
      if (keepCR != null)
      {
        uri.addParameter("keep_cr", String.valueOf(keepCR));
      }
      if (ignoreSelfLoops != null)
      {
        uri.addParameter("ignore_self_loops", String.valueOf(ignoreSelfLoops));
      }
      if (ignoreDuplicateLinks != null)
      {
        uri.addParameter("ignore_duplicate_links", String.valueOf(ignoreDuplicateLinks));
      }
      if (level != null)
      {
        uri.addParameter("level", String.valueOf(level));
      }
      HttpPost post = new HttpPost(uri.build());
      post.setEntity(linklist);
      Clustering result = this.execute(client, post, Clustering.class);
      this.LOG.trace("END: clusterLinkList");
      return result;
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  // public final boolean nodeNodeMatrix(String id, String name, String description, HttpEntity
  // nodeNodeMatrix,
  // String separator, String quoteChar, String escapeChar, Integer skipLines, Boolean strictQuotes,
  // Boolean ignoreLeadingWhiteSpace, Boolean keepCR, Boolean rowHeader, Boolean ignoreSelfLoops)
  // {
  // try
  // {
  // this.LOG.trace("START: nodeNodeMatrix(TODO PARAMETERS)");
  // HttpClient client = this.getClient("text/plain", "application/json");
  // URIBuilder uri = new URIBuilder(this.url + "/io/nodenodematrix");
  // uri.addParameter("network_id", id);
  // uri.addParameter("name", name);
  // uri.addParameter("description", description);
  // if (separator != null)
  // {
  // uri.addParameter("separator", separator);
  // }
  // if (quoteChar != null)
  // {
  // uri.addParameter("quote_char", quoteChar);
  // }
  // if (escapeChar != null)
  // {
  // uri.addParameter("escape_char", escapeChar);
  // }
  // if (skipLines != null)
  // {
  // uri.addParameter("skip_lines", String.valueOf(skipLines));
  // }
  // if (strictQuotes != null)
  // {
  // uri.addParameter("strict_quotes", String.valueOf(strictQuotes));
  // }
  // if (ignoreLeadingWhiteSpace != null)
  // {
  // uri.addParameter("ignore_leading_white_space", String.valueOf(ignoreLeadingWhiteSpace));
  // }
  // if (keepCR != null)
  // {
  // uri.addParameter("keep_cr", String.valueOf(keepCR));
  // }
  // if (rowHeader != null)
  // {
  // uri.addParameter("row_header", String.valueOf(rowHeader));
  // }
  // if (ignoreSelfLoops != null)
  // {
  // uri.addParameter("ignore_self_loops", String.valueOf(ignoreSelfLoops));
  // }
  // HttpPost post = new HttpPost(uri.build());
  // post.setEntity(nodeNodeMatrix);
  // boolean result = this.execute(client, post);
  // this.LOG.trace("END: importNodeNodeMatrix");
  // return result;
  // }
  // catch (Exception e)
  // {
  // throw new RuntimeException(e);
  // }
  // }

  @SuppressWarnings("unchecked")
  private final <T> T toClass(HttpResponse response, Class<T> clazz)
  {
    String json = null;
    try
    {
      json = this.toString(response);
      if (clazz == String.class)
      {
        return (T) json;
      }
      return new ObjectMapper().readValue(json, clazz);
    }
    catch (Exception e)
    {
      this.LOG.trace("Response was: {}", json);
      throw new RuntimeException(e);
    }
  }

  private final <T> T toType(HttpResponse response, TypeReference<T> typeRef)
  {
    String json = null;
    try
    {
      json = this.toString(response);
      return new ObjectMapper().readValue(json, typeRef);
    }
    catch (Exception e)
    {
      this.LOG.trace("Response was: {}", json);
      throw new RuntimeException(e);
    }
  }

  private final String toString(HttpResponse response)
  {
    try
    {
      if (response.getEntity() != null)
      {
        return IOUtils.toString(response.getEntity().getContent(), "UTF-8");
      }
      return null;
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  private final StringEntity toJSON(Object o)
  {
    try
    {
      ObjectMapper mapper = new ObjectMapper();
      String entity = mapper.writeValueAsString(o);
      return new StringEntity(entity);
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  private final HttpClient getClient(String contentType, String accept)
  {
    return new NDHttpClient(this.apiKey, contentType, accept, this.userToken, this.adminKey);
  }

  private final boolean execute(HttpClient client, HttpUriRequest request)
  {
    try
    {
      this.LOG.trace("START: execute(client={}, request={}", client, request);
      HttpResponse response = client.execute(request);
      boolean result = false;
      if (response.getStatusLine().getStatusCode() == HttpStatus.SC_BAD_REQUEST)
      {
        ExceptionInfo info = this.toClass(response, ExceptionInfo.class);
        this.LOG.error("Bad request. {}", info);
        throw new NDAPIException(info);
      }
      else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR)
      {
        String message = null;
        try
        {
          message = IOUtils.toString(response.getEntity().getContent());
        }
        catch (EOFException e)
        {
          message = "[NO RESPONSE MESSAGE CONTENT]";
        }
        this.LOG.error("Internal server error: {}", message);
        throw new NDAPIError();
      }
      else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT)
      {
        result = true;
      }
      this.LOG.trace("END: execute");
      return result;
    }
    catch (NDAPIException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  private final HttpResponse execute(HttpUriRequest request, HttpClient client)
  {
    try
    {
      this.LOG.trace("START: execute(client={}, request={}", client, request);
      HttpResponse response = client.execute(request);
      if (response.getStatusLine().getStatusCode() == HttpStatus.SC_BAD_REQUEST)
      {
        ExceptionInfo info = this.toClass(response, ExceptionInfo.class);
        this.LOG.error("Bad request. {}", info);
        throw new NDAPIException(info);
      }
      else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR)
      {
        String message = null;
        try
        {
          message = IOUtils.toString(response.getEntity().getContent());
        }
        catch (EOFException e)
        {
          message = "[NO RESPONSE MESSAGE CONTENT]";
        }
        this.LOG.error("Internal server error: {}", message);
        throw new NDAPIError();
      }
      this.LOG.trace("END: execute");
      return response;
    }
    catch (NDAPIException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  private final <T> T execute(HttpClient client, HttpUriRequest request, TypeReference<T> resultClass)
  {
    try
    {
      this.LOG.trace("START: execute(client={}, request={}, resultClass={}", client, request, resultClass);
      HttpResponse response = client.execute(request);
      T result = null;
      if (response.getStatusLine().getStatusCode() == HttpStatus.SC_BAD_REQUEST)
      {
        ExceptionInfo info = this.toClass(response, ExceptionInfo.class);
        this.LOG.error("Bad request. {}", info);
        throw new NDAPIException(info);
      }
      else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR)
      {
        String message = null;
        try
        {
          message = IOUtils.toString(response.getEntity().getContent());
        }
        catch (EOFException e)
        {
          message = "[NO RESPONSE MESSAGE CONTENT]";
        }
        this.LOG.error("Internal server error: {}", message);
        throw new NDAPIError();
      }
      else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
      {
        result = this.toType(response, resultClass);
      }
      this.LOG.trace("END: execute");
      return result;
    }
    catch (NDAPIException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  private final <T> T execute(HttpClient client, HttpUriRequest request, Class<T> resultClass)
  {
    try
    {
      this.LOG.trace("START: execute(client={}, request={}, resultClass={}", client, request, resultClass);
      HttpResponse response = client.execute(request);
      T result = null;
      if (response.getStatusLine().getStatusCode() == HttpStatus.SC_BAD_REQUEST)
      {
        ExceptionInfo info = this.toClass(response, ExceptionInfo.class);
        this.LOG.error("Bad request. {}", info);
        throw new NDAPIException(info);
      }
      else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR)
      {
        String message = null;
        try
        {
          message = IOUtils.toString(response.getEntity().getContent());
        }
        catch (EOFException e)
        {
          message = "[NO RESPONSE MESSAGE CONTENT]";
        }
        this.LOG.error("Internal server error: {}", message);
        throw new NDAPIError();
      }
      else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
      {
        result = this.toClass(response, resultClass);
      }
      this.LOG.trace("END: execute");
      return result;
    }
    catch (NDAPIException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  private final URIBuilder newURIBuilder(String uri)
  {
    try
    {
      return new URIBuilder(uri);
    }
    catch (URISyntaxException e)
    {
      throw new RuntimeException(e);
    }
  }

  private final URI buildUri(URIBuilder builder)
  {
    try
    {
      return builder.build();
    }
    catch (URISyntaxException e)
    {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Map<String, List<Integer>> listTriangles(String id, String name, String description, HttpEntity linklist,
      Integer firstEndpointColumnIndex, Integer secondEndpointColumnIndex, String separator, String quoteChar,
      String escapeChar, Integer skipLines, Boolean strictQuotes, Boolean ignoreLeadingWhiteSpace, Boolean keepCR,
      Boolean ignoreSelfLoops, Boolean ignoreDuplicateLinks)
  {
    try
    {
      this.LOG.trace("START: listTriangles(TODO PARAMETERS)");
      HttpClient client = this.getClient("text/plain", "application/json");
      URIBuilder uri = new URIBuilder(this.url + "/analytics/triangles");
      uri.addParameter("id", id);
      uri.addParameter("name", name);
      uri.addParameter("description", description);
      if (firstEndpointColumnIndex != null)
      {
        uri.addParameter("first_endpoint_column_index", String.valueOf(firstEndpointColumnIndex));
      }
      if (secondEndpointColumnIndex != null)
      {
        uri.addParameter("second_endpoint_column_index", String.valueOf(secondEndpointColumnIndex));
      }
      if (separator != null)
      {
        uri.addParameter("separator", separator);
      }
      if (quoteChar != null)
      {
        uri.addParameter("quote_char", quoteChar);
      }
      if (escapeChar != null)
      {
        uri.addParameter("escape_char", escapeChar);
      }
      if (skipLines != null)
      {
        uri.addParameter("skip_lines", String.valueOf(skipLines));
      }
      if (strictQuotes != null)
      {
        uri.addParameter("strict_quotes", String.valueOf(strictQuotes));
      }
      if (ignoreLeadingWhiteSpace != null)
      {
        uri.addParameter("ignore_leading_white_space", String.valueOf(ignoreLeadingWhiteSpace));
      }
      if (keepCR != null)
      {
        uri.addParameter("keep_cr", String.valueOf(keepCR));
      }
      if (ignoreSelfLoops != null)
      {
        uri.addParameter("ignore_self_loops", String.valueOf(ignoreSelfLoops));
      }
      if (ignoreDuplicateLinks != null)
      {
        uri.addParameter("ignore_duplicate_links", String.valueOf(ignoreDuplicateLinks));
      }
      HttpPost post = new HttpPost(uri.build());
      post.setEntity(linklist);
      TypeReference<Map<String, List<Integer>>> type = new TypeReference<Map<String, List<Integer>>>()
      {};
      Map<String, List<Integer>> result = this.execute(client, post, type);
      this.LOG.trace("END: listTriangles");
      return result;
    }
    catch (NDAPIException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Tree extractSubtree(String id, String name, String description, HttpEntity linklist,
      Integer firstEndpointColumnIndex, Integer secondEndpointColumnIndex, String separator, String quoteChar,
      String escapeChar, Integer skipLines, Boolean strictQuotes, Boolean ignoreLeadingWhiteSpace, Boolean keepCR,
      Boolean ignoreSelfLoops, Boolean ignoreDuplicateLinks, String subtreeRootId, String parentNodeId, Integer height)
  {
    try
    {
      this.LOG.trace("START: extractSubtree(TODO PARAMETERS)");
      HttpClient client = this.getClient("text/plain", "application/json");
      URIBuilder uri = new URIBuilder(this.url + "/analytics/subtree");
      uri.addParameter("id", id);
      uri.addParameter("name", name);
      uri.addParameter("description", description);
      uri.addParameter("subtreeRootId", subtreeRootId);
      if (firstEndpointColumnIndex != null)
      {
        uri.addParameter("first_endpoint_column_index", String.valueOf(firstEndpointColumnIndex));
      }
      if (secondEndpointColumnIndex != null)
      {
        uri.addParameter("second_endpoint_column_index", String.valueOf(secondEndpointColumnIndex));
      }
      if (separator != null)
      {
        uri.addParameter("separator", separator);
      }
      if (quoteChar != null)
      {
        uri.addParameter("quote_char", quoteChar);
      }
      if (escapeChar != null)
      {
        uri.addParameter("escape_char", escapeChar);
      }
      if (skipLines != null)
      {
        uri.addParameter("skip_lines", String.valueOf(skipLines));
      }
      if (strictQuotes != null)
      {
        uri.addParameter("strict_quotes", String.valueOf(strictQuotes));
      }
      if (ignoreLeadingWhiteSpace != null)
      {
        uri.addParameter("ignore_leading_white_space", String.valueOf(ignoreLeadingWhiteSpace));
      }
      if (keepCR != null)
      {
        uri.addParameter("keep_cr", String.valueOf(keepCR));
      }
      if (ignoreSelfLoops != null)
      {
        uri.addParameter("ignore_self_loops", String.valueOf(ignoreSelfLoops));
      }
      if (ignoreDuplicateLinks != null)
      {
        uri.addParameter("ignore_duplicate_links", String.valueOf(ignoreDuplicateLinks));
      }
      if (height != null)
      {
        uri.addParameter("height", String.valueOf(height));
      }
      if (parentNodeId != null)
      {
        uri.addParameter("parentNodeId", parentNodeId);
      }
      HttpPost post = new HttpPost(uri.build());
      post.setEntity(linklist);
      Tree result = this.execute(client, post, Tree.class);
      this.LOG.trace("END: extractSubtree");
      return result;
    }
    catch (NDAPIException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Layout layoutTreeLinkList(String id, String name, String description, HttpEntity linklist,
      Integer firstEndpointColumnIndex, Integer secondEndpointColumnIndex, String separator, String quoteChar,
      String escapeChar, Integer skipLines, Boolean strictQuotes, Boolean ignoreLeadingWhiteSpace, Boolean keepCR,
      Boolean ignoreSelfLoops, Boolean ignoreDuplicateLinks, String subtreeRootId, String parentNodeId, Integer height,
      TreeLayoutStyle style)
  {
    try
    {
      this.LOG.trace("START: layoutTreeLinkList(TODO PARAMETERS)");
      HttpClient client = this.getClient("text/plain", "application/json");
      URIBuilder uri = new URIBuilder(this.url + "/layout/linklist/tree");
      uri.addParameter("id", id);
      uri.addParameter("name", name);
      uri.addParameter("description", description);
      uri.addParameter("subtreeRootId", subtreeRootId);
      if (firstEndpointColumnIndex != null)
      {
        uri.addParameter("first_endpoint_column_index", String.valueOf(firstEndpointColumnIndex));
      }
      if (secondEndpointColumnIndex != null)
      {
        uri.addParameter("second_endpoint_column_index", String.valueOf(secondEndpointColumnIndex));
      }
      if (separator != null)
      {
        uri.addParameter("separator", separator);
      }
      if (quoteChar != null)
      {
        uri.addParameter("quote_char", quoteChar);
      }
      if (escapeChar != null)
      {
        uri.addParameter("escape_char", escapeChar);
      }
      if (skipLines != null)
      {
        uri.addParameter("skip_lines", String.valueOf(skipLines));
      }
      if (strictQuotes != null)
      {
        uri.addParameter("strict_quotes", String.valueOf(strictQuotes));
      }
      if (ignoreLeadingWhiteSpace != null)
      {
        uri.addParameter("ignore_leading_white_space", String.valueOf(ignoreLeadingWhiteSpace));
      }
      if (keepCR != null)
      {
        uri.addParameter("keep_cr", String.valueOf(keepCR));
      }
      if (ignoreSelfLoops != null)
      {
        uri.addParameter("ignore_self_loops", String.valueOf(ignoreSelfLoops));
      }
      if (ignoreDuplicateLinks != null)
      {
        uri.addParameter("ignore_duplicate_links", String.valueOf(ignoreDuplicateLinks));
      }
      if (height != null)
      {
        uri.addParameter("height", String.valueOf(height));
      }
      if (parentNodeId != null)
      {
        uri.addParameter("parentNodeId", parentNodeId);
      }
      if (style != null)
      {
        uri.addParameter("style", style.name());
      }
      HttpPost post = new HttpPost(uri.build());
      post.setEntity(linklist);
      Layout result = this.execute(client, post, Layout.class);
      this.LOG.trace("END: layoutTreeLinkList");
      return result;
    }
    catch (NDAPIException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }
}
