package com.skillupnow.demo.exception;

/**
 * The ValidationGroups class contains validation groups used for validating input data in the application.
 * The groups are defined as interfaces with no methods.
 *
 * @author Hua Wang
 */
public class ValidationGroups {

  /**
   * The Insert interface is a validation group for validating input data when creating a new entity.
   */
  public interface Insert{};

  /**
   * The Update interface is a validation group for validating input data when updating an existing entity.
   */
  public interface Update{};
}
