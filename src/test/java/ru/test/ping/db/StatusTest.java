package ru.test.ping.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.test.ping.repo.StatusRepository;

@SpringBootTest
class StatusTest {

@Autowired
  private StatusRepository statusRepository;

  @Test
  void getAllStatuses() {
    Assertions.assertEquals(3, statusRepository.findAll().size());
  }
}
