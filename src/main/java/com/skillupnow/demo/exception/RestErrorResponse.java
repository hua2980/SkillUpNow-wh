package com.skillupnow.demo.exception;

import java.io.Serializable;

/**
 * A uniform structure for the Restful api error response
 *
 * @author Hua Wang
 */
public class RestErrorResponse implements Serializable {
  private String errMessage;

  /**
   * Constructor
   * @param errMessage error message
   */
  public RestErrorResponse(String errMessage) {
    this.errMessage = errMessage;
  }

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
  public void setErrMessage(String errMessage) {
    this.errMessage = errMessage;
  }
}
