package com.petrov.databases.repository;

import com.petrov.databases.entity.credit.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<Credit, Long> {
}
