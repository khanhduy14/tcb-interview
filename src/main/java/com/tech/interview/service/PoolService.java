package com.tech.interview.service;

import com.tech.interview.dto.request.QueryDTO;
import com.tech.interview.dto.request.UpdateDTO;
import com.tech.interview.dto.response.QueryResDTO;
import com.tech.interview.dto.response.UpdateResDTO;

public interface PoolService {
  UpdateResDTO update(UpdateDTO input);
  QueryResDTO query(QueryDTO input);
}
