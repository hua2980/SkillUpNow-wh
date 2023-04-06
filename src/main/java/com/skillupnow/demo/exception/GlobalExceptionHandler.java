package com.skillupnow.demo.exception;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Processing common errors
   * @param exception common exception being caught
   * @return RestErrorResponse
   */
  @ResponseBody
  @ExceptionHandler(SkillUpNowException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public RestErrorResponse commonExceptionHandler(SkillUpNowException exception) {
    return new RestErrorResponse(exception.getErrMessage());
  }
}
