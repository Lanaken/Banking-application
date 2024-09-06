package com.petrov.databases.repository;

import com.petrov.databases.entity.debitaccount.DebitAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DebitAccountRepository extends JpaRepository<DebitAccount, Long> {
    Optional<DebitAccount> findByAccountNumber(String accountNumber);
}
