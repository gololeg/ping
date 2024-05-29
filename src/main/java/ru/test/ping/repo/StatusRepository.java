package ru.test.ping.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.test.ping.entities.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {

}
