package ru.test.ping.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "statuses")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Status {

  public static Status STATUS_1 = Status.builder().id(1).build();
  public static Status STATUS_2 = Status.builder().id(2).build();
  public static Status STATUS_3 = Status.builder().id(3).build();
  @Id
  private int id;

  @Column(name = "name")
  private String name;
}
