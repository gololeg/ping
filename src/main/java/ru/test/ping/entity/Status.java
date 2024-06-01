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

  @Id
  private int id;

  @Column(name = "name")
  private String name;
}
