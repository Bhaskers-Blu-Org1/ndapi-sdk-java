package com.ibm.research.nd.rest.sdk.api.objects;

public class TreeNodeInfo
{
  private int descendants;
  private int depth;
  private int height;

  public int getDescendants()
  {
    return this.descendants;
  }

  public void setDescendants(int descendants)
  {
    this.descendants = descendants;
  }


  public int getDepth()
  {
    return this.depth;
  }

  public void setDepth(int depth)
  {
    this.depth = depth;
  }

  public int getHeight()
  {
    return this.height;
  }

  public void setHeight(int height)
  {
    this.height = height;
  }
}
