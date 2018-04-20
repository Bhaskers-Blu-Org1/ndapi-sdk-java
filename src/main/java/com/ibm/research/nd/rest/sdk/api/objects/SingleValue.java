package com.ibm.research.nd.rest.sdk.api.objects;

public class SingleValue
{
  private Object value;

  public SingleValue()
  {

  }

  public SingleValue(int value)
  {
    this.value = value;
  }

  public SingleValue(boolean value)
  {
    this.value = value;
  }

  public SingleValue(String value)
  {
    this.value = value;
  }

  public SingleValue(double value)
  {
    this.value = value;
  }

  public SingleValue(Object value)
  {
    this.value = value;
  }

  public Object getValue()
  {
    return this.value;
  }

  public void setValue(Object value)
  {
    this.value = value;
  }
}
