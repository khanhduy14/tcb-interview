package com.tech.interview.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tech.interview.dto.request.QueryDTO;
import com.tech.interview.dto.request.UpdateDTO;
import com.tech.interview.model.PoolModel;
import com.tech.interview.storage.PoolStorage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PoolControllerTest {

  @Autowired
  private MockMvc mvc;

  @Test
  public void insertedSuccessTest() throws Exception {

    UpdateDTO updateDTO = new UpdateDTO();
    updateDTO.setPoolId(1);
    updateDTO.setPoolValues(new ArrayList<>(Arrays.asList(1, 2, 3, 4)));

    String requestJson = convertObjectToString(updateDTO);

    mvc.perform(MockMvcRequestBuilders.post("/update").content(requestJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(equalTo("INSERTED")));
  }

  @Test
  public void appendedSuccessTest() throws Exception {

    UpdateDTO updateDTO = new UpdateDTO();
    updateDTO.setPoolId(1);
    updateDTO.setPoolValues(new ArrayList<>(Arrays.asList(1, 2, 3, 4)));

    String requestJson = convertObjectToString(updateDTO);

    mvc.perform(MockMvcRequestBuilders.post("/update").content(requestJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(equalTo("APPENDED")));
  }

  @Test
  public void querySuccessTest() throws Exception {

    PoolModel model = PoolModel.of(2, new ArrayList<>(Arrays.asList(1, 2, 3, 4)));
    PoolStorage.set(model);

    QueryDTO queryDTO = new QueryDTO();
    queryDTO.setPoolId(2);
    queryDTO.setPercentile(50.0);

    String requestJson = convertObjectToString(queryDTO);

    mvc.perform(MockMvcRequestBuilders.post("/query").content(requestJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void queryNotFoundTest() throws Exception {

    QueryDTO queryDTO = new QueryDTO();
    queryDTO.setPoolId(3);
    queryDTO.setPercentile(50.0);

    String requestJson = convertObjectToString(queryDTO);

    mvc.perform(MockMvcRequestBuilders.post("/query").content(requestJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void appendedFailedWithInvalidBodyTest() throws Exception {

    UpdateDTO updateDTO = new UpdateDTO();
    updateDTO.setPoolId(null);
    updateDTO.setPoolValues(new ArrayList<>(Arrays.asList(1, 2, 3, 4)));

    String requestJson = convertObjectToString(updateDTO);

    mvc.perform(MockMvcRequestBuilders.post("/update").content(requestJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

  private String convertObjectToString(Object object) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    return ow.writeValueAsString(object);
  }

}