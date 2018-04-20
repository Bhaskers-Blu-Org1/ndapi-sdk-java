package com.ibm.research.nd.rest.sdk.api.objects;

import java.util.Map;
import java.util.Set;

public class Tree
{
  private Set<Pair<String, String>> links;
  private Map<String, TreeNodeInfo> nodes;

  public Map<String, TreeNodeInfo> getNodes()
  {
    return this.nodes;
  }

  public void setNodes(Map<String, TreeNodeInfo> nodes)
  {
    this.nodes = nodes;
  }

  public Set<Pair<String, String>> getLinks()
  {
    return this.links;
  }

  public void setLinks(Set<Pair<String, String>> links)
  {
    this.links = links;
  }
}
