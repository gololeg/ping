package ru.test.ping.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.test.ping.entities.Operation;

public interface OperationRepository extends JpaRepository<Operation, Long> {

}
