package ru.test.ping.db;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.test.ping.entities.Operation;
import ru.test.ping.repo.OperationRepository;
import ru.test.ping.repo.StatusRepository;

@SpringBootTest
class OperationTest {

  @Autowired
  private OperationRepository operationRepository;
  @Autowired
  private StatusRepository statusRepository;

  @Test
  @Transactional
  void saveAndGet() {
    Operation op = operationRepository.save(
        Operation.builder().domain("domain").createDate(LocalDateTime.now())
            .status(statusRepository.getReferenceById(1))
            .build());
    Operation dbOperation = operationRepository.getReferenceById(op.getId());
    Assertions.assertEquals(op.getDomain(), dbOperation.getDomain());
  }
}
