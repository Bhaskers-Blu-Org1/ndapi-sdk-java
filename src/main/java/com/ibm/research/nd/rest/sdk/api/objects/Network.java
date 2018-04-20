package com.ibm.research.nd.rest.sdk.api.objects;

import java.util.Map;
import java.util.Set;

public class Network
{

  private String                           id;
  private String                           name;
  private String                           description;
  private Set<Node>                        nodes;
  private Set<Link>                        links;

  private Map<String, Map<String, String>> nodeAttributes;
  private Map<String, Map<String, String>> linkAttributes;
  private Map<String, AttributeType>       nodeAttributeTypes;
  private Map<String, AttributeType>       linkAttributeTypes;
  private Map<String, String>              nodeAttributeLabels;
  private Map<String, String>              linkAttributeLabels;

  public String getId()
  {
    return this.id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getDescription()
  {
    return this.description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public Set<Node> getNodes()
  {
    return this.nodes;
  }

  public void setNodes(Set<Node> nodes)
  {
    this.nodes = nodes;
  }

  public Set<Link> getLinks()
  {
    return this.links;
  }

  public void setLinks(Set<Link> links)
  {
    this.links = links;
  }

  public Map<String, Map<String, String>> getNodeAttributes()
  {
    return this.nodeAttributes;
  }

  public void setNodeAttributes(Map<String, Map<String, String>> nodeAttributes)
  {
    this.nodeAttributes = nodeAttributes;
  }

  public Map<String, Map<String, String>> getLinkAttributes()
  {
    return this.linkAttributes;
  }

  public void setLinkAttributes(Map<String, Map<String, String>> linkAttributes)
  {
    this.linkAttributes = linkAttributes;
  }

  public Map<String, AttributeType> getNodeAttributeTypes()
  {
    return this.nodeAttributeTypes;
  }

  public void setNodeAttributeTypes(Map<String, AttributeType> nodeAttributeTypes)
  {
    this.nodeAttributeTypes = nodeAttributeTypes;
  }

  public Map<String, AttributeType> getLinkAttributeTypes()
  {
    return this.linkAttributeTypes;
  }

  public void setLinkAttributeTypes(Map<String, AttributeType> linkAttributeTypes)
  {
    this.linkAttributeTypes = linkAttributeTypes;
  }

  public Map<String, String> getNodeAttributeLabels()
  {
    return this.nodeAttributeLabels;
  }

  public void setNodeAttributeLabels(Map<String, String> nodeAttributeLabels)
  {
    this.nodeAttributeLabels = nodeAttributeLabels;
  }

  public Map<String, String> getLinkAttributeLabels()
  {
    return this.linkAttributeLabels;
  }

  public void setLinkAttributeLabels(Map<String, String> linkAttributeLabels)
  {
    this.linkAttributeLabels = linkAttributeLabels;
  }

  @Override
  public String toString()
  {
    return this.id;
  }
}
