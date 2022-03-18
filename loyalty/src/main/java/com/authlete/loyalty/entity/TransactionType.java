package com.authlete.loyalty.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TransactionType {
  @JsonProperty("Cash Redemption")
  CASH_REDEMPTION,

  @JsonProperty("Earned Points")
  EARNED_POINTS,

  @JsonProperty("Purchase Redemption")
  PURCHASE_REDEMPTION
}
