package ru.test.ping.params;

import lombok.Data;

@Data
public class ParamsRequestOperation {

  private String domain;
  private String dateFrom;
  private String dateTo;
  private int statusId;
}
