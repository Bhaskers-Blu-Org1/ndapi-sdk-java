package com.ibm.research.nd.rest.sdk.api;

import com.ibm.research.nd.rest.sdk.api.objects.ExceptionInfo;

public class NDAPIError extends NDAPIException
{
  private static final long serialVersionUID = 1L;

  public NDAPIError()
  {
    super(new ExceptionInfo(9999, "Internal error", "Internal Error"));
  }
}
