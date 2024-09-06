package com.petrov.databases.repository;

import com.petrov.databases.entity.debitcard.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebitCardRepository extends JpaRepository<DebitCard, Long> {
}
