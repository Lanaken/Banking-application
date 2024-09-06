package com.petrov.databases.repository;

import com.petrov.databases.entity.credit.CreditPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditPaymentRepository extends JpaRepository<CreditPayment, Long> {
}
