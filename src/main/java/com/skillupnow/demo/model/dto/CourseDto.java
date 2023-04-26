package com.skillupnow.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDto {
  @JsonProperty
  private Long id;

  @JsonProperty
  private String name;
}
