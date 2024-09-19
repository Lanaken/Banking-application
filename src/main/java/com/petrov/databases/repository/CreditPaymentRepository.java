package com.petrov.databases.repository;

import com.petrov.databases.entity.credit.CreditPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditPaymentRepository extends JpaRepository<CreditPayment, Long> {
    List<CreditPayment> findByCreditUuid(String creditUuid);
}
