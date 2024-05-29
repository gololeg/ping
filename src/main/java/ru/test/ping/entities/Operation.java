package ru.test.ping.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "operations")
@Data
@Builder
public class Operation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "domain")
  private String domain;

  @Column(name = "create_date")
  private LocalDateTime createDate;

  @ManyToOne
  @JoinColumn(name = "status_id")
  private Status status;

  @Column(name = "result")
  private String result;

}
