package com.infoshareacademy.model;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.*;

@Entity
@Table(name = "COURSES")
public class Course {

  @Id
  @Column(name = "name", length = 16)
  private String name;

  @ManyToMany(mappedBy = "courses")
  private List<Student> students;

  @ManyToMany(mappedBy = "courses")
  private List<Teacher> teachers;

  public Course() {
  }

  public Course(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Student> getStudents() {
    return students;
  }

  public void setStudents(List<Student> students) {
    this.students = students;
  }

  public List<Teacher> getTeachers() {
    return teachers;
  }

  public void setTeachers(List<Teacher> teachers) {
    this.teachers = teachers;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("Course{");
    sb.append("name='").append(name).append('\'');
    sb.append(", students=").append(students != null ? students
        .stream()
        .map(Student::getSurname)
        .collect(Collectors.joining(", ")) : "");
    sb.append(", teachers=").append(teachers != null ? teachers
            .stream()
            .map(Teacher::getSurname)
            .collect(Collectors.joining(", ")) : "");
    sb.append('}');
    return sb.toString();  }

}


