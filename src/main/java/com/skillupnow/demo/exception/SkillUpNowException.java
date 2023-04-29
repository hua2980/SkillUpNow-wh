package com.skillupnow.demo.exception;

/**
 * The SkillUpNowException class is a custom exception class used for throwing exceptions in the application.
 * It extends RuntimeException and adds an error message field.
 *
 * @author Hua Wang
 */
public class SkillUpNowException extends RuntimeException{

  private String errMessage;

  /**
   * Constructor
   */
  public SkillUpNowException() {
    super();
  }

  /**
   * Constructor
   * @param errMessage error message
   */
  public SkillUpNowException(String errMessage) {
    super(errMessage);
    this.errMessage = errMessage;
  }

  /**
   * Get error message
   * @return error message
   */
  public String getErrMessage() {
    return errMessage;
  }

  /**
   * Throws a new SkillUpNowException with the error message from the given CommonError object.
   * @param commonError The CommonError object containing the error message.
   */
  public static void cast(CommonError commonError){
    throw new SkillUpNowException(commonError.getErrMessage());
  }

  /**
   * Throws a new SkillUpNowException with the specified error message.
   * @param errMessage The error message for the new SkillUpNowException.
   */
  public static void cast(String errMessage){
    throw new SkillUpNowException(errMessage);
  }
}
