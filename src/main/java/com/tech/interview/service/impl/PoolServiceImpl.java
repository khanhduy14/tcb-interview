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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class PoolServiceImpl implements PoolService {

  @Override
  public UpdateResDTO update(UpdateDTO input) {
    if (PoolStorage.get(input.getPoolId()) == null) {
      ArrayList<Integer> values = input.getPoolValues().stream().sorted().collect(Collectors.toCollection(ArrayList::new));
      PoolStorage.set(PoolModel.of(input.getPoolId(), values));
      return UpdateResDTO.of(UpdateStatus.INSERTED);
    } else {
      PoolModel model = PoolStorage.get(input.getPoolId());
      ArrayList<Integer> values = joinValues(model.getPoolValues(), input.getPoolValues());
      model.setPoolValues(values);
      PoolStorage.set(PoolModel.of(model.getPoolId(), model.getPoolValues()));
      return UpdateResDTO.of(UpdateStatus.APPENDED);
    }
  }

  @Override
  public QueryResDTO query(QueryDTO input) {
    if (PoolStorage.get(input.getPoolId()) == null) {
      throw new BaseException(HttpStatus.NOT_FOUND, "PoolId " + input.getPoolId() + " not found");
    } else {
      PoolModel model = PoolStorage.get(input.getPoolId());
      int count = model.getPoolValues().size();
      if (input.getPercentile() == 0) {
        return QueryResDTO.of(model.getPoolValues().get(0), count);
      }
      if (input.getPercentile() == 100) {
        return QueryResDTO.of(model.getPoolValues().get(count - 1), count);
      }
      int quantile = (int) Math.round((double) (count) * input.getPercentile() / 100.0);
      return QueryResDTO.of(model.getPoolValues().get(quantile - 1), count);
    }
  }

  private ArrayList<Integer> joinValues(ArrayList<Integer> firstList, ArrayList<Integer> secondList) {
    firstList.addAll(secondList);
    return firstList.stream().sorted().collect(Collectors.toCollection(ArrayList::new));
  }
}
