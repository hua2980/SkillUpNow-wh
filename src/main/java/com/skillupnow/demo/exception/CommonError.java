package com.skillupnow.demo.exception;

public enum CommonError {
  UNKNOWN_ERROR("unknown error occurred, please try again later."),
  OBJECT_NULL("object is null"),
  REQUEST_NULL("request is null"),
  PARAMS_ERROR("invalid params");

  private String errMessage;

  public String getErrMessage() {
    return errMessage;
  }

  CommonError(String errMessage) {
    this.errMessage = errMessage;
  }
}
