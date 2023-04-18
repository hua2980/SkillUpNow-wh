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

@Entity
@Table(name = "course")
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
  private List<TeachPlan> teachPlans;

  public Course() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPic() {
    return pic;
  }

  public void setPic(String pic) {
    this.pic = pic;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getOriginalPrice() {
    return originalPrice;
  }

  public void setOriginalPrice(BigDecimal originalPrice) {
    this.originalPrice = originalPrice;
  }

  public List<TeachPlan> getTeachPlans() {
    return teachPlans;
  }

  public void setTeachPlans(List<TeachPlan> teachPlans) {
    this.teachPlans = teachPlans;
  }

  public Organization getOrganization() {
    return organization;
  }

  public void setOrganization(Organization organization) {
    this.organization = organization;
  }

  @JsonProperty("organization_name")
  public String getOrganizationName() {
    return organization.getOrganizationName();
  }
}
