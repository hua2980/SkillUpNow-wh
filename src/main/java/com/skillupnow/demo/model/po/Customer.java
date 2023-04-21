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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
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

  public Customer(String username, String password, UserType userType, Cart cart) {
    super(username, password, userType);
    this.cart = cart;
  }

  public Customer(String username, String password, UserType userType) {
    super(username, password, userType);
    this.cart = new Cart();
  }

  public Customer() {
    super();
  }

  public Cart getCart() {
    return cart;
  }

  public void setCart(Cart cart) {
    this.cart = cart;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getHeadline() {
    return headline;
  }

  public void setHeadline(String headline) {
    this.headline = headline;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  public void removeOrder(Order order) {
    if (this.orders == null) this.orders = new ArrayList<Order>();
    else this.orders.remove(order);
  }
}
