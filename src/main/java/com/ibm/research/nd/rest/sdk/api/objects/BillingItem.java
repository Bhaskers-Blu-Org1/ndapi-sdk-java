package com.ibm.research.nd.rest.sdk.api.objects;

public class BillingItem
{
  private String unit;
  private int    amount;

  public BillingItem()
  {

  }

  public String getUnit()
  {
    return this.unit;
  }

  public void setUnit(String unit)
  {
    this.unit = unit;
  }

  public int getAmount()
  {
    return this.amount;
  }

  public void setAmount(int amount)
  {
    this.amount = amount;
  }

  @Override
  public String toString()
  {
    return this.amount + " " + this.unit;
  }
}
