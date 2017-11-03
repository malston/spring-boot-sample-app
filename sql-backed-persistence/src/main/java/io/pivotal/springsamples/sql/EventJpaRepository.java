package io.pivotal.springsamples.sql;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventJpaRepository extends CrudRepository<EventJpaEntity, String> {
    List<EventJpaEntity> findByDateBetween(LocalDate start, LocalDate end);
}
