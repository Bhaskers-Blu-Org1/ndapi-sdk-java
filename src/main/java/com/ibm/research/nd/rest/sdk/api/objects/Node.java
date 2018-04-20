package com.ibm.research.nd.rest.sdk.api.objects;

public class Node
{
  private String id;

  public Node()
  {

  }

  public Node(String id)
  {
    this.id = id;
  }

  public String getId()
  {
    return this.id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (this.getClass() != obj.getClass())
    {
      return false;
    }
    Node other = (Node) obj;
    if (this.id == null)
    {
      if (other.id != null)
      {
        return false;
      }
    }
    else
      if (!this.id.equals(other.id))
      {
        return false;
      }
    return true;
  }

  @Override
  public String toString()
  {
    return this.id;
  }
}
