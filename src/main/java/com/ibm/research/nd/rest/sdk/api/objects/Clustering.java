package com.ibm.research.nd.rest.sdk.api.objects;

import java.util.Collection;
import java.util.Map;

/**
 * Node to cluster and node to component assignments.
 *
 *
 * @author Daniel K. Weidele
 *
 *         Mar 16, 2017
 */
public class Clustering
{
  /**
   * Component assignments.
   */
  private Map<Integer, Collection<String>> components;

  /**
   * Cluster assignments
   */
  private Map<Integer, Collection<String>> clusters;

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
   * Node to cluster assignments.
   *
   * @return
   */
  public Map<Integer, Collection<String>> getClusters()
  {
    return this.clusters;
  }

  /**
   * Node to cluster assignments.
   */
  public void setClusters(Map<Integer, Collection<String>> clusters)
  {
    this.clusters = clusters;
  }

  @Override
  public String toString()
  {
    return "Clustering (#comp=" + this.components.size() + ", #clust=" + this.clusters.size() + ")";
  }
}
