package com.petrov.databases.repository;

import com.petrov.databases.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);
    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.debitAccounts a LEFT JOIN FETCH a.debitCards WHERE c.email = :email")
    Client findByEmailWithAccountsAndCards(String email);
    Optional<Client> findByPassportSeriesAndPassportNumber(String passportSeries, String passportNumber);
}
