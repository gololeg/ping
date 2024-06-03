package ru.test.ping.service;

import static ru.test.ping.entity.Status.STATUS_COMPLITED;
import static ru.test.ping.entity.Status.STATUS_RUNNABLE;
import static ru.test.ping.entity.Status.STATUS_RUNNING;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.test.ping.entity.Operation;
import ru.test.ping.entity.Status;
import ru.test.ping.params.ParamsRequestOperation;
import ru.test.ping.repo.OperationRepository;

@Service
public class OperationService {

  final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private OperationRepository operationRepository;

  public OperationService(OperationRepository operationRepository) {
    this.operationRepository = operationRepository;
  }

  public Page<Operation> findAll(ParamsRequestOperation params, Pageable pageable) {
    String domain = params.getDomain() == null ? "" : params.getDomain();
    LocalDateTime start;
    try {
      start = LocalDateTime.parse(params.getDateFrom() == null ? "" : params.getDateFrom(), dtf);
    } catch (DateTimeParseException ex) {
      start = LocalDateTime.now().minusYears(100);
    }

    LocalDateTime end;
    try {
      end = LocalDateTime.parse(params.getDateTo() == null ? "" : params.getDateTo(), dtf);
    } catch (DateTimeParseException ex) {
      end = LocalDateTime.now();
    }
    List<Status> statuses =
        params.getStatusId() == 0 ? List.of(STATUS_RUNNABLE, STATUS_RUNNING, STATUS_COMPLITED)
            : List.of(Status.builder().id(params.getStatusId()).build());
    return operationRepository.findByDomainContainingIgnoreCaseAndCreateDateBetweenAndStatusInOrderByCreateDateDesc(
        domain, start, end, statuses, pageable

    );
  }
}
