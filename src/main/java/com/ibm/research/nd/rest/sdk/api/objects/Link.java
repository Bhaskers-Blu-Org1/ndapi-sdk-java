package com.ibm.research.nd.rest.sdk.api.objects;

public class Link
{
  private String id;
  private String from;
  private String to;

  public Link()
  {

  }

  public Link(String id, String from, String to)
  {
    this.id = id;
    this.from = from;
    this.to = to;
  }

  public String getId()
  {
    return this.id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getFrom()
  {
    return this.from;
  }

  public void setFrom(String from)
  {
    this.from = from;
  }

  public String getTo()
  {
    return this.to;
  }

  public void setTo(String to)
  {
    this.to = to;
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
    Link other = (Link) obj;
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
    return this.id + " [" + this.from + " -> " + this.to + "]";
  }
}
