package com.skillupnow.demo.model.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "course")
@Getter
@Setter
public class Course implements Serializable{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty
  private Long id;

  @ManyToOne
  @JoinColumn(name = "organization_id", referencedColumnName = "id")
  @JsonIgnore
  private Organization organization;

  @Column(name = "name", nullable = false)
  @JsonProperty
  private String name;

  @Column(name = "course_type", nullable = false)
  @JsonProperty
  private String courseType;

  @Column(name = "description")
  @JsonProperty
  private String description;

  @Column(name = "pic")
  @JsonProperty
  private String pic;

  @Column(name = "price")
  @JsonProperty
  private BigDecimal price;

  @Column(name = "original_price")
  @JsonProperty
  private BigDecimal originalPrice;

  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
  @JsonIgnore
  @ToString.Exclude
  private List<TeachPlan> teachPlans;

  public Course() {
  }

  @JsonProperty("organization_name")
  public String getOrganizationName() {
    return organization.getOrganizationName();
  }
}
