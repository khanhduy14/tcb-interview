package com.tech.interview.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class PoolModel {
  private Integer poolId;
  private ArrayList<Integer> poolValues;

  public static PoolModel of(Integer poolId, ArrayList<Integer> poolValues) {
    PoolModel poolModel = new PoolModel();
    poolModel.setPoolId(poolId);
    poolModel.setPoolValues(poolValues);
    return poolModel;
  }
}
