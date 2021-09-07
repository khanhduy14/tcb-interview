package com.tech.interview.dto.response;

import lombok.Data;

@Data
public class QueryResDTO {
  private Integer quantile;
  private Integer count;
  public static QueryResDTO of(Integer quantile, Integer count) {
    QueryResDTO dto = new QueryResDTO();
    dto.setCount(count);
    dto.setQuantile(quantile);
    return dto;
  }
}
