package com.ibm.research.nd.rest.sdk.api.objects;

public class VertexBillingItem extends BillingItem
{
  private static final String UNIT = "vertex";

  public VertexBillingItem(int amount)
  {
    this.setUnit(VertexBillingItem.UNIT);
    this.setAmount(amount);
  }

}
