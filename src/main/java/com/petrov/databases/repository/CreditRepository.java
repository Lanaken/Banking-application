package com.petrov.databases.repository;

import com.petrov.databases.entity.credit.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CreditRepository extends JpaRepository<Credit, Long> {
    @Query("SELECT c FROM Credit c LEFT JOIN FETCH c.creditPayments WHERE c.uuid = :uuid")
    Credit findByUuidWithPayments(String uuid);
    Credit findByUuid(String uuid);
    
}
