package com.ibm.research.nd.rest.sdk.api.objects;

public enum ValueType
{
  DISTANCE, SIMILARITY;

  public ValueType fromValue(String value)
  {
    return ValueType.valueOf(value.toUpperCase());
  }

  public ValueType fromString(String value)
  {
    return ValueType.valueOf(value.toUpperCase());
  }
}
