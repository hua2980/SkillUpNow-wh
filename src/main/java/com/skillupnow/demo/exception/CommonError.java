package com.skillupnow.demo.exception;

/**
 * common error
 * 1. UNKNOWN_ERROR: unknown error occurred, please try again later.
 * 2. OBJECT_NULL: object is null
 * 3. REQUEST_NULL: request is null
 * 4. PARAMS_ERROR: invalid params
 *
 * @author Hua Wang
 */
public enum CommonError {
  UNKNOWN_ERROR("unknown error occurred, please try again later."),
  OBJECT_NULL("object is null"),
  REQUEST_NULL("request is null"),
  PARAMS_ERROR("invalid params");

  private String errMessage;

  /**
   * get error message
   * @return error message
   */
  public String getErrMessage() {
    return errMessage;
  }

  /**
   * set error message
   * @param errMessage error message
   */
  CommonError(String errMessage) {
    this.errMessage = errMessage;
  }
}
