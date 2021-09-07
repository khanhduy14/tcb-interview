package com.tech.interview.controller;

import com.tech.interview.dto.request.QueryDTO;
import com.tech.interview.dto.request.UpdateDTO;
import com.tech.interview.dto.response.QueryResDTO;
import com.tech.interview.dto.response.UpdateResDTO;
import com.tech.interview.service.PoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping()
@RestController
@Slf4j
public class PoolController {

  private final PoolService service;

  public PoolController(PoolService service) {
    this.service = service;
  }

  @PostMapping(path = "/update")
  public ResponseEntity<String> update(@Valid @RequestBody UpdateDTO input) {
    log.info("(update)object: {}", input);
    UpdateResDTO result = service.update(input);
    return ResponseEntity.ok(result.getStatus().toString());
  }

  @PostMapping(path = "/query")
  public ResponseEntity<QueryResDTO> query(@Valid @RequestBody QueryDTO input) {
    log.info("(query)object: {}", input);
    QueryResDTO result = service.query(input);
    if (result == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(result);
  }
}
