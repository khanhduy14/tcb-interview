package com.tech.interview.dto.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class QueryDTO {
  @NotNull
  @Min(1)
  private Integer poolId;

  @NotNull
  @Min(0)
  @Max(100)
  private Double percentile;
}
