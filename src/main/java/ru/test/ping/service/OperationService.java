package ru.test.ping.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.test.ping.ParamsRequestOperation;
import ru.test.ping.entity.Operation;
import ru.test.ping.entity.Status;
import ru.test.ping.repo.OperationRepository;

@Service
public class OperationService {

  private OperationRepository operationRepository;

  public OperationService(OperationRepository operationRepository) {
    this.operationRepository = operationRepository;
  }

  public Page<Operation> findAll(ParamsRequestOperation params, Pageable pageable) {
    String domain = params.getDomain() == null ? "" : params.getDomain();
    LocalDateTime start = LocalDateTime.now().minusYears(100);
    LocalDateTime end = LocalDateTime.now();
    List<Status> statuses = List.of(Status.STATUS_1, Status.STATUS_2, Status.STATUS_3);
    return operationRepository.findByDomainContainingIgnoreCaseAndCreateDateBetweenAndStatusInOrderByCreateDateDesc(
        domain, start, end, statuses, pageable

    );
  }
}
