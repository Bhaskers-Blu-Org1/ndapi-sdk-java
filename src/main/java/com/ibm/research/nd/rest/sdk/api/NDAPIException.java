package com.ibm.research.nd.rest.sdk.api;

import com.ibm.research.nd.rest.sdk.api.objects.ExceptionInfo;

public class NDAPIException extends RuntimeException
{
  private static final long serialVersionUID = 1L;

  private ExceptionInfo     info;

  public NDAPIException(ExceptionInfo info)
  {
    super(info.getDescription());
    this.info = info;
  }

  public ExceptionInfo getInfo()
  {
    return this.info;
  }
}
