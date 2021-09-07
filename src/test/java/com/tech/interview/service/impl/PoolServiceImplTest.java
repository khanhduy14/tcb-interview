package com.tech.interview.service.impl;

import com.tech.interview.constant.UpdateStatus;
import com.tech.interview.dto.request.UpdateDTO;
import com.tech.interview.dto.response.UpdateResDTO;
import com.tech.interview.model.PoolModel;
import com.tech.interview.service.PoolService;
import com.tech.interview.storage.PoolStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

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
    input.setPoolId(1);
    input.setPoolValues(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5)));
    Mockito.when(PoolStorage.get(1)).thenReturn(null);
    UpdateResDTO result = service.update(input);
    Assertions.assertEquals(result, UpdateResDTO.of(UpdateStatus.INSERTED));
  }

  @Test
  void updatePoolExisted() {
  }

  @Test
  void queryPoolNotExisted() {
  }

  @Test
  void queryPoolExisted() {
  }
}