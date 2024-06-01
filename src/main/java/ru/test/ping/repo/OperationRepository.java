package ru.test.ping.repo;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.test.ping.entity.Operation;
import ru.test.ping.entity.Status;

public interface OperationRepository extends JpaRepository<Operation, Long> {

  Page<Operation> findByDomainContainingIgnoreCaseAndCreateDateBetweenAndStatusIn(String domain,
      LocalDateTime start, LocalDateTime end, List<Status> statuses, Pageable pageable);

}
