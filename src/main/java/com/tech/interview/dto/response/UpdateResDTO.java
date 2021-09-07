package com.tech.interview.dto.response;

import com.tech.interview.constant.UpdateStatus;
import lombok.Data;

@Data
public class UpdateResDTO {
  private UpdateStatus status;
  public static UpdateResDTO of (UpdateStatus status) {
    UpdateResDTO dto = new UpdateResDTO();
    dto.setStatus(status);
    return dto;
  }
}
