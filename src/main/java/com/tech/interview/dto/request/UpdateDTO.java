package com.tech.interview.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;

@Data
public class UpdateDTO {
  @NotNull
  private Integer poolId;

  @NotNull
  private ArrayList<Integer> poolValues;
}
