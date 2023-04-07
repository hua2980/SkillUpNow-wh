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

  /**
   * Processing Spring's MethodArgumentNotValidException exception
   * @param exception exception being caught
   * @return RestErrorResponse
   */
  @ResponseBody
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public RestErrorResponse commentExceptionHandler(MethodArgumentNotValidException exception) {
    // Store error messages
    List<String> errors = exception.getBindingResult()
        .getFieldErrors().stream()
        .map(ObjectError::getDefaultMessage)
        .collect(Collectors.toList());

    // Concatenate error messages
    StringBuffer errMsg = new StringBuffer();
    errors.forEach(error -> {
      errMsg.append(error).append(", ");
    });
    if (errMsg.length() > 0) errMsg.setLength(errMsg.length() - 2);

    return new RestErrorResponse(errMsg.toString());
  }
}
