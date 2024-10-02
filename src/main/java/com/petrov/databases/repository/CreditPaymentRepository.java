package com.petrov.databases.repository;

import com.petrov.databases.entity.credit.CreditPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CreditPaymentRepository extends JpaRepository<CreditPayment, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM test.credit_payment p where p.credit_id = :creditUuid order by p.payment_date desc")
    List<CreditPayment> findByCreditUuid(String creditUuid);

    @Query(value = "Select * from test.credit_payment p where p.credit_id = :creditUuid and p.was_done = false order by p.payment_date desc limit 1", nativeQuery = true)
    CreditPayment findNearestNotClosedCreditPayment(String creditUuid);

//    @Query(nativeQuery = true, value = "SELECT \n" +
//            "    SUM(cp.deposited_amount), \n" +
//            "    EXTRACT(YEAR FROM cp.payment_date) AS year, \n" +
//            "    TO_CHAR(cp.payment_date, 'Month') AS month \n" +
//            "FROM \n" +
//            "    test.client cl \n" +
//            "JOIN \n" +
//            "    test.debit_account da ON cl.id = da.client_id \n" +
//            "JOIN \n" +
//            "    test.credit cr ON da.id = cr.debit_account_id\n" +
//            "JOIN \n" +
//            "    test.credit_payment cp ON cr.\"uuid\" = cp.credit_id \n" +
//            "where cl.id = 1\n" +
//            "GROUP BY \n" +
//            "    year, month\n" +
//            "ORDER BY \n" +
//            "    year, month")

}
