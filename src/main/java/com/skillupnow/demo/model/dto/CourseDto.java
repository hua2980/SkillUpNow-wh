package com.skillupnow.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * The CourseDto class represents the data transfer object for the Course entity.
 * It includes the Course's id and name fields.
 *
 * @author Hua Wang
 */
@Getter
@Setter
public class CourseDto {
  @JsonProperty
  private Long id;

  @JsonProperty
  private String name;
}
