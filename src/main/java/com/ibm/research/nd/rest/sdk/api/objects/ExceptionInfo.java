package com.ibm.research.nd.rest.sdk.api.objects;

public class ExceptionInfo
{
  private int    code;
  private String description;
  private String details;

  public ExceptionInfo()
  {

  }

  public ExceptionInfo(int code, String description, String details)
  {
    this.code = code;
    this.description = description;
    this.details = details;
  }

  public int getCode()
  {
    return this.code;
  }

  public String getDescription()
  {
    return this.description;
  }

  public String getDetails()
  {
    return this.details;
  }

  public void setCode(int code)
  {
    this.code = code;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public void setDetails(String details)
  {
    this.details = details;
  }

  @Override
  public String toString()
  {
    return this.code + ": " + this.description + (this.details != null ? " [" + this.details + "]" : "");
  }
}
