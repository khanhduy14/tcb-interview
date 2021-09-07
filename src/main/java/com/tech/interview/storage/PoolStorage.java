package com.tech.interview.storage;

import com.tech.interview.exception.BaseException;
import com.tech.interview.model.PoolModel;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class PoolStorage {

  private static final HashMap<Integer, PoolModel> poolMap = new HashMap<>();

  public static PoolModel get(Integer key) {
    return poolMap.get(key);
  }

  public static PoolModel set(PoolModel value) {
    return poolMap.putIfAbsent(value.getPoolId(), value);
  }

  public static void clear() {
    poolMap.clear();
  }
}
