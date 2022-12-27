package ru.sladkkov.gasstation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.sladkkov.gasstation.model.Topology;


@Repository
public interface TopologyRepository extends JpaRepository<Topology, Long> {
}
