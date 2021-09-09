package com.tech.interview.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

import java.util.ArrayList;

@Data
public class UpdateDTO {
  @NotNull
  @Min(1)
  private Integer poolId;

  @NotNull
  @Size(min = 1)
  private ArrayList<Integer> poolValues;
}
