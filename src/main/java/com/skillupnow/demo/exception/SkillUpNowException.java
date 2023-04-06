package com.skillupnow.demo.exception;

public class SkillUpNowException extends RuntimeException{

  private String errMessage;

  public SkillUpNowException() {
    super();
  }

  public SkillUpNowException(String errMessage) {
    super(errMessage);
    this.errMessage = errMessage;
  }

  public String getErrMessage() {
    return errMessage;
  }

  public static void cast(CommonError commonError){
    throw new SkillUpNowException(commonError.getErrMessage());
  }

  public static void cast(String errMessage){
    throw new SkillUpNowException(errMessage);
  }
}
