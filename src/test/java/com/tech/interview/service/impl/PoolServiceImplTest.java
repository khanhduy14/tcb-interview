package com.tech.interview.service.impl;

import com.tech.interview.constant.UpdateStatus;
import com.tech.interview.dto.request.QueryDTO;
import com.tech.interview.dto.request.UpdateDTO;
import com.tech.interview.dto.response.QueryResDTO;
import com.tech.interview.dto.response.UpdateResDTO;
import com.tech.interview.exception.BaseException;
import com.tech.interview.model.PoolModel;
import com.tech.interview.service.PoolService;
import com.tech.interview.storage.PoolStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;


@SpringBootTest
class PoolServiceImplTest {

  @Autowired
  private PoolService service;

  @BeforeEach
  void setUp() {
    for (int i = 0; i < 10; i++) {
      PoolStorage.set(PoolModel.of(i, generateList()));
    }
  }

  @AfterEach
  void tearDown() {
    PoolStorage.clear();
  }

  private ArrayList<Integer> generateList() {
    return new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  }

  @Test
  void updatePoolNotExisted() {
    UpdateDTO input = new UpdateDTO();
    input.setPoolId(11);
    input.setPoolValues(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5)));
    UpdateResDTO result = service.update(input);
    Assertions.assertEquals(result, UpdateResDTO.of(UpdateStatus.INSERTED));
  }

  @Test
  void updatePoolExisted() {
    UpdateDTO input = new UpdateDTO();
    input.setPoolId(1);
    input.setPoolValues(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5)));
    UpdateResDTO result = service.update(input);
    Assertions.assertEquals(result, UpdateResDTO.of(UpdateStatus.APPENDED));
  }

  @Test
  void queryPoolExisted() {
    QueryDTO input = new QueryDTO();
    input.setPercentile(50.0);
    input.setPoolId(3);
    QueryResDTO expected = new QueryResDTO();
    expected.setQuantile(5);
    expected.setCount(10);
    QueryResDTO result = service.query(input);
    Assertions.assertEquals(result, expected);
  }

  @Test
  void queryPoolNotExisted() {
    QueryDTO input = new QueryDTO();
    input.setPercentile(50.0);
    input.setPoolId(13);
    Assertions.assertThrows(BaseException.class, () -> service.query(input));
  }
}