package com.skillupnow.demo.model.po;

import static org.junit.jupiter.api.Assertions.*;

import com.skillupnow.demo.model.UserType;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains test cases for the Organization.
 *
 * @author Hua Wang
 */
public class OrganizationTest {
  private Organization organization;

  /**
   * This method sets up the test environment by initializing the Organization instance.
   */
  @BeforeEach
  public void setUp() {
    organization = new Organization("test", "password", UserType.ORGANIZATION);
  }

  /**
   * Tests the getter and setter methods of the Organization class.
   */
  @Test
  public void testGettersAndSetters() {

    // Normal case
    assertEquals("default", organization.getOrganizationName()); // default value for organizationName

    organization.setOrganizationName("Test Organization");

    Course course1 = new Course();
    course1.setId(1L);
    course1.setName("Course 1");
    Course course2 = new Course();
    course2.setId(2L);
    course2.setName("Course 2");

    List<Course> courses = new ArrayList<>();
    courses.add(course1);
    courses.add(course2);

    organization.setCourses(courses);

    assertEquals("Test Organization", organization.getOrganizationName());
    assertEquals(courses, organization.getCourses());
  }

  /**
   * Tests the constructors of the Organization class.
   */
  @Test
  void testConstructors() {
    Organization organization1 = new Organization();
    assertEquals("default", organization1.getOrganizationName()); // default value for organizationName
    assertNotNull(organization1.getCourses());
    assertEquals(0, organization1.getCourses().size());  // default value for courses
    assertNull(organization1.password);
    assertNull(organization1.username);
    assertNotNull(organization1.userType);
    assertEquals(UserType.ORGANIZATION, organization1.userType); // default values for userType


    Course course1 = new Course();
    course1.setId(1L);
    course1.setName("Course 1");
    Course course2 = new Course();
    course2.setId(2L);
    course2.setName("Course 2");

    List<Course> courses = new ArrayList<>();
    courses.add(course1);
    courses.add(course2);

    Organization organization2 = new Organization("test", "password", UserType.ORGANIZATION, courses, "Test Organization");
    assertEquals("Test Organization", organization2.getOrganizationName());
    assertEquals(courses, organization2.getCourses());
    assertEquals("test", organization2.getUsername());
    assertEquals("password", organization2.getPassword());
    assertEquals(UserType.ORGANIZATION, organization2.getUserType());
  }
}

