package com.ibm.research.nd.rest.sdk.api.objects;

import java.util.Collection;
import java.util.Map;

/**
 * Layout coordinates and node to component assignments.
 *
 *
 * @author Daniel K. Weidele
 *
 *         Oct 29, 2016
 */
public class Layout
{
  /**
   * Layout coordinates.
   */
  private Map<String, double[]>            coordinates;

  /**
   * Component assignments.
   */
  private Map<Integer, Collection<String>> components;

  /**
   * Node information.
   */
  private Map<String, Map<String, Object>> nodeInfos;

  /**
   * Layout coordinates.
   *
   * @return
   */
  public Map<String, double[]> getCoordinates()
  {
    return this.coordinates;
  }

  /**
   * Layout coordinates.
   *
   * @param coordinates
   */
  public void setCoordinates(Map<String, double[]> coordinates)
  {
    this.coordinates = coordinates;
  }

  /**
   * Node to component assignments.
   *
   * @return
   */
  public Map<Integer, Collection<String>> getComponents()
  {
    return this.components;
  }

  /**
   * Node to component assignments.
   *
   * @param components
   */
  public void setComponents(Map<Integer, Collection<String>> components)
  {
    this.components = components;
  }

  /**
   * Node information.
   *
   * @return
   */
  public Map<String, Map<String, Object>> getNodeInfos()
  {
    return this.nodeInfos;
  }

  /**
   * Node information.
   *
   * @param nodeInfos
   */
  public void setNodeInfos(Map<String, Map<String, Object>> nodeInfos)
  {
    this.nodeInfos = nodeInfos;
  }

  @Override
  public String toString()
  {
    return "Layout (n=" + this.coordinates.size() + ", c=" + this.components.size() + ")";
  }
}
