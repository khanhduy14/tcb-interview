package com.tech.interview.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class QueryDTO {
  @NotNull
  private Integer poolId;

  @NotNull
  private Double percentile;
}
