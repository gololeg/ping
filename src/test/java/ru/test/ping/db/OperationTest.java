package ru.test.ping.db;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import ru.test.ping.entity.Operation;
import ru.test.ping.entity.Status;
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

  @Test
  @Transactional
  void paging() {
    for (int i = 0; i < 11; i++) {
      operationRepository.save(
          Operation.builder().domain("domain").createDate(LocalDateTime.now())
              .status(statusRepository.getReferenceById(1))
              .build());
    }
    Assertions.assertEquals(5,
        operationRepository.findByDomainContainingIgnoreCaseAndCreateDateBetweenAndStatusInOrderByCreateDateDesc(
            "",LocalDateTime.now().minusSeconds(2), LocalDateTime.now(), List.of(Status.builder()
                .id(1).build()),
            PageRequest.of(1, 5)).getContent().size());

  }
}
