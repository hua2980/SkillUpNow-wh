package com.skillupnow.demo.model.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.skillupnow.demo.model.UserType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "customer")
@Getter
@Setter
public class Customer extends User implements Serializable {
  private static final long serialVersionUID = 1L;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "cart_id", referencedColumnName = "id")
  @JsonIgnore
  private Cart cart;

  @JsonProperty
  private String firstname;

  @JsonProperty
  private String lastname;

  @JsonProperty
  private String headline;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JsonIgnore
  private List<Order> orders;

  /**
   * Constructs a new Customer object with the given parameters.
   *
   * @param username The username of the customer.
   * @param password The password of the customer.
   * @param userType The user type of the customer.
   * @param cart     The cart associated with the customer.
   */
  public Customer(String username, String password, UserType userType, Cart cart) {
    super(username, password, userType);
    this.cart = cart;
  }

  /**
   * Constructs a new Customer object with the given parameters.
   *
   * @param username The username of the customer.
   * @param password The password of the customer.
   * @param userType The user type of the customer.
   */
  public Customer(String username, String password, UserType userType) {
    super(username, password, userType);
    this.cart = new Cart();
  }

  /**
   * Default constructor for the Customer class.
   */
  public Customer() {
    super();
  }

  /**
   * Removes the specified order from the customer's list of orders.
   *
   * @param order The order to be removed.
   */
  public void removeOrder(Order order) {
    if (this.orders == null) this.orders = new ArrayList<Order>();
    else this.orders.remove(order);
  }

  /**
   * Adds the specified order to the customer's list of orders.
   *
   * @param order The order to be added.
   */

  public void addOrder(Order order) {
    if (this.orders == null) this.orders = new ArrayList<Order>();
    this.orders.add(order);
  }
}
