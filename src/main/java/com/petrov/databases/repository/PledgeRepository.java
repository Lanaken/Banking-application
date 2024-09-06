package com.petrov.databases.repository;

import com.petrov.databases.entity.pledge.Pledge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PledgeRepository extends JpaRepository<Pledge, Long> {
}
