package com.ibm.research.nd.rest.sdk.api;

import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;

import com.ibm.research.nd.rest.sdk.api.objects.BillingItem;
import com.ibm.research.nd.rest.sdk.api.objects.Clustering;
import com.ibm.research.nd.rest.sdk.api.objects.Layout;
import com.ibm.research.nd.rest.sdk.api.objects.Tree;
import com.ibm.research.nd.rest.sdk.api.objects.TreeLayoutStyle;
import com.ibm.research.nd.rest.sdk.api.objects.User;
import com.ibm.research.nd.rest.sdk.api.objects.ValueType;

/**
 * Network Data API alpha
 *
 *
 * @author Daniel K. Weidele
 */
public interface NetworkDataAPI
{
  /**
   * Ping the service.
   *
   * @return <code>true</code> if the service is reachable.
   */
  boolean ping();

  /**
   * Render a network layout from a CSV link list input.
   *
   * @param id ID of your network
   * @param name Name of your network.
   * @param description Description of your network.
   * @param linklist List of links (.csv input data)
   * @param dimensions Dimensionality for the layout. Default is <code>2</code>.
   * @param distanceFactor Factor between closest and most distant nodes in layout. Default is
   *        <code>10</code>.
   * @param adaptNeighborhoodSimilarity Adapt link values to node neighborhood similarity. Default
   *        is <code>true</code>.
   * @param firstEndpointColumnIndex Column index of the first end point node identifiers. Default
   *        is <code>0</code> .
   * @param secondEndpointColumnIndex Column index of the second end point node identifiers. Default
   *        is <code>1</code> .
   * @param valued Indicates whether the input contains a column for link values. If
   *        <code>false</code>, link values default to to 1. Default is <code>false</code>.
   * @param valueColumnIndex Column index of the link value attribute. These values need to be
   *        numeric. Default is <code>2</code>.
   * @param valueType Type of the link value. Values can represent a <code>SIMILARITY</code> or
   *        <code>DISTANCE</code> between nodes. Default is <code>DISTANCE</code>.
   * @param separator Separator character for columns. Default is <code>,</code>.
   * @param quoteChar Quote character for column values. Default is <code>'</code>.
   * @param escapeChar Character to escape separator or quote character. Default is <code>\</code>.
   * @param skipLines Number of lines to skip before reading. Default is <code>0</code>.
   * @param strictQuotes If <code>true</code> characters outside quotes will be ignored. Default is
   *        <code>false</code>.
   * @param ignoreLeadingWhiteSpace If <code>true</code> leading white-spaces will be ignored.
   *        Default is <code>true</code>.
   * @param keepCR If <code>true</code> parser carriage returns will be kept. Default is
   *        <code>false</code>.
   * @param ignoreSelfLoops If <code>false</code> parser will throw an error when detecting self
   *        loops. Otherwise self loops will be ignored. Default is <code>false</code>.
   * @param ignoreDuplicateLinks If <code>false</code> parser will throw an error when detecting
   *        duplicate links. Otherwise links duplicating the first occurence will be ignored.
   *        Default is <code>false</code>.
   * @return {@link Layout}
   */
  Layout layoutLinkList(String id, String name, String description, HttpEntity linklist, Integer dimensions,
      Double distanceFactor, Boolean adaptNeighborhoodSimilarity, Integer firstEndpointColumnIndex,
      Integer secondEndpointColumnIndex, Boolean valued, Integer valueColumnIndex, ValueType valueType,
      String separator, String quoteChar, String escapeChar, Integer skipLines, Boolean strictQuotes,
      Boolean ignoreLeadingWhiteSpace, Boolean keepCR, Boolean ignoreSelfLoops, Boolean ignoreDuplicateLinks);

  /**
   * Layout a subtree from the network provided as CSV link list input.
   *
   * @param id ID of your network
   * @param name Name of your network.
   * @param description Description of your network.
   * @param linklist List of links (.csv input data)
   * @param firstEndpointColumnIndex Column index of the first end point node identifiers. Default
   *        is <code>0</code> .
   * @param secondEndpointColumnIndex Column index of the second end point node identifiers. Default
   *        is <code>1</code> .
   * @param separator Separator character for columns. Default is <code>,</code>.
   * @param quoteChar Quote character for column values. Default is <code>'</code>.
   * @param escapeChar Character to escape separator or quote character. Default is <code>\</code>.
   * @param skipLines Number of lines to skip before reading. Default is <code>0</code>.
   * @param strictQuotes If <code>true</code> characters outside quotes will be ignored. Default is
   *        <code>false</code>.
   * @param ignoreLeadingWhiteSpace If <code>true</code> leading white-spaces will be ignored.
   *        Default is <code>true</code>.
   * @param keepCR If <code>true</code> parser carriage returns will be kept. Default is
   *        <code>false</code>.
   * @param ignoreSelfLoops If <code>false</code> parser will throw an error when detecting self
   *        loops. Otherwise self loops will be ignored. Default is <code>false</code>.
   * @param ignoreDuplicateLinks If <code>false</code> parser will throw an error when detecting
   *        duplicate links. Otherwise links duplicating the first occurrence will be ignored.
   *        Default is <code>false</code>.
   * @param subtreeRootId The ID of the node to become the root of the subtree.
   * @param parentNodeId ID of the parent of the subtree root. Default is null.
   * @param height The desired height of the subtree. If <code>-1</code> the full subtree will be
   *        returned. Default is 0.
   * @param style The {@link TreeLayoutStyle} of the layout. Default is 'BALLOON'.
   * @return {@link Tree} Subtree of specified height with given node as root.
   */
  Layout layoutTreeLinkList(String id, String name, String description, HttpEntity linklist,
      Integer firstEndpointColumnIndex, Integer secondEndpointColumnIndex, String separator, String quoteChar,
      String escapeChar, Integer skipLines, Boolean strictQuotes, Boolean ignoreLeadingWhiteSpace, Boolean keepCR,
      Boolean ignoreSelfLoops, Boolean ignoreDuplicateLinks, String subtreeRootId, String parentNodeId, Integer height,
      TreeLayoutStyle style);

  /**
   * Cluster a network from a CSV link list input.
   *
   * @param id ID of your network
   * @param name Name of your network.
   * @param description Description of your network.
   * @param linklist List of links (.csv input data)
   * @param firstEndpointColumnIndex Column index of the first end point node identifiers. Default
   *        is <code>0</code> .
   * @param secondEndpointColumnIndex Column index of the second end point node identifiers. Default
   *        is <code>1</code> .
   * @param valued Indicates whether the input contains a column for link values. If
   *        <code>false</code>, link values default to to 1. Default is <code>false</code>.
   * @param valueColumnIndex Column index of the link value attribute. These values need to be
   *        numeric. Default is <code>2</code>.
   * @param valueType Type of the link value. Values can represent a <code>SIMILARITY</code> or
   *        <code>DISTANCE</code> between nodes. Default is <code>DISTANCE</code>.
   * @param separator Separator character for columns. Default is <code>,</code>.
   * @param quoteChar Quote character for column values. Default is <code>'</code>.
   * @param escapeChar Character to escape separator or quote character. Default is <code>\</code>.
   * @param skipLines Number of lines to skip before reading. Default is <code>0</code>.
   * @param strictQuotes If <code>true</code> characters outside quotes will be ignored. Default is
   *        <code>false</code>.
   * @param ignoreLeadingWhiteSpace If <code>true</code> leading white-spaces will be ignored.
   *        Default is <code>true</code>.
   * @param keepCR If <code>true</code> parser carriage returns will be kept. Default is
   *        <code>false</code>.
   * @param ignoreSelfLoops If <code>false</code> parser will throw an error when detecting self
   *        loops. Otherwise self loops will be ignored. Default is <code>false</code>.
   * @param ignoreDuplicateLinks If <code>false</code> parser will throw an error when detecting
   *        duplicate links. Otherwise links duplicating the first occurence will be ignored.
   *        Default is <code>false</code>.
   * @param level Output level of the clustering hierarchy. Default is 1.
   * @return {@link Clustering}
   */
  Clustering clusterLinkList(String id, String name, String description, HttpEntity linklist,
      Integer firstEndpointColumnIndex, Integer secondEndpointColumnIndex, Boolean valued, Integer valueColumnIndex,
      ValueType valueType, String separator, String quoteChar, String escapeChar, Integer skipLines,
      Boolean strictQuotes, Boolean ignoreLeadingWhiteSpace, Boolean keepCR, Boolean ignoreSelfLoops,
      Boolean ignoreDuplicateLinks, Integer level);

  /**
   * Login an existing user and retrieve a user token.
   *
   * @param email The email of the user to be logged in.
   * @param password The user's password.
   */
  void login(String email, String password);

  /**
   * Register a new user.
   *
   * @param user The user to be registered.
   */
  void register(User user);

  /**
   * Set the user's daily limit for a billing item. The limit per day is the basis for the projected
   * monthly quota. Operations will fail if, for a given billing item, the user's projected monthly
   * quota would be exceeded.
   *
   * @param email The e-mail address of the user to set the limit for.
   * @param limit The billing item limit.
   */
  void limitUser(String email, BillingItem limit);

  /**
   * Set the default daily limit for a billing item for new users. The limit per day is the basis
   * for the projected monthly quota. Operations will fail if, for a given billing item, the user's
   * projected monthly quota would be exceeded.
   *
   * @param limit The billing item limit.
   */
  void limitDefault(BillingItem limit);

  /**
   * Send a code to the user's email address with which the password can be reset.
   *
   * @param email The user's email address.
   */
  void sendResetPasswordCode(String email);

  /**
   * Reset a user's password using the code sent by email.
   *
   * @param email The user's email address.
   * @param code The code the user received by email.
   * @param newPassword The new password.
   */
  void resetPassword(String email, String code, String newPassword);

  /**
   * List all triangles in the network provided as CSV link list input.
   *
   * @param id ID of your network
   * @param name Name of your network.
   * @param description Description of your network.
   * @param linklist List of links (.csv input data)
   * @param firstEndpointColumnIndex Column index of the first end point node identifiers. Default
   *        is <code>0</code> .
   * @param secondEndpointColumnIndex Column index of the second end point node identifiers. Default
   *        is <code>1</code> .
   * @param separator Separator character for columns. Default is <code>,</code>.
   * @param quoteChar Quote character for column values. Default is <code>'</code>.
   * @param escapeChar Character to escape separator or quote character. Default is <code>\</code>.
   * @param skipLines Number of lines to skip before reading. Default is <code>0</code>.
   * @param strictQuotes If <code>true</code> characters outside quotes will be ignored. Default is
   *        <code>false</code>.
   * @param ignoreLeadingWhiteSpace If <code>true</code> leading white-spaces will be ignored.
   *        Default is <code>true</code>.
   * @param keepCR If <code>true</code> parser carriage returns will be kept. Default is
   *        <code>false</code>.
   * @param ignoreSelfLoops If <code>false</code> parser will throw an error when detecting self
   *        loops. Otherwise self loops will be ignored. Default is <code>false</code>.
   * @param ignoreDuplicateLinks If <code>false</code> parser will throw an error when detecting
   *        duplicate links. Otherwise links duplicating the first occurence will be ignored.
   *        Default is <code>false</code>.
   * @return {@link Map} Map of node IDs to their list of triangle IDs.
   */
  Map<String, List<Integer>> listTriangles(String id, String name, String description, HttpEntity linklist,
      Integer firstEndpointColumnIndex, Integer secondEndpointColumnIndex, String separator, String quoteChar,
      String escapeChar, Integer skipLines, Boolean strictQuotes, Boolean ignoreLeadingWhiteSpace, Boolean keepCR,
      Boolean ignoreSelfLoops, Boolean ignoreDuplicateLinks);

  /**
   * Extract a subtree from the network provided as CSV link list input.
   *
   * @param id ID of your network
   * @param name Name of your network.
   * @param description Description of your network.
   * @param linklist List of links (.csv input data)
   * @param firstEndpointColumnIndex Column index of the first end point node identifiers. Default
   *        is <code>0</code> .
   * @param secondEndpointColumnIndex Column index of the second end point node identifiers. Default
   *        is <code>1</code> .
   * @param separator Separator character for columns. Default is <code>,</code>.
   * @param quoteChar Quote character for column values. Default is <code>'</code>.
   * @param escapeChar Character to escape separator or quote character. Default is <code>\</code>.
   * @param skipLines Number of lines to skip before reading. Default is <code>0</code>.
   * @param strictQuotes If <code>true</code> characters outside quotes will be ignored. Default is
   *        <code>false</code>.
   * @param ignoreLeadingWhiteSpace If <code>true</code> leading white-spaces will be ignored.
   *        Default is <code>true</code>.
   * @param keepCR If <code>true</code> parser carriage returns will be kept. Default is
   *        <code>false</code>.
   * @param ignoreSelfLoops If <code>false</code> parser will throw an error when detecting self
   *        loops. Otherwise self loops will be ignored. Default is <code>false</code>.
   * @param ignoreDuplicateLinks If <code>false</code> parser will throw an error when detecting
   *        duplicate links. Otherwise links duplicating the first occurrence will be ignored.
   *        Default is <code>false</code>.
   * @param subtreeRootId The ID of the node to become the root of the subtree.
   * @param parentNodeId ID of the parent of the subtree root. Default is null.
   * @param height The desired height of the subtree. If <code>-1</code> the full subtree will be
   *        returned. Default is 0.
   * @return {@link Tree} Subtree of specified height with given node as root.
   */
  Tree extractSubtree(String id, String name, String description, HttpEntity linklist, Integer firstEndpointColumnIndex,
      Integer secondEndpointColumnIndex, String separator, String quoteChar, String escapeChar, Integer skipLines,
      Boolean strictQuotes, Boolean ignoreLeadingWhiteSpace, Boolean keepCR, Boolean ignoreSelfLoops,
      Boolean ignoreDuplicateLinks, String subtreeRootId, String parentNodeId, Integer height);
}
