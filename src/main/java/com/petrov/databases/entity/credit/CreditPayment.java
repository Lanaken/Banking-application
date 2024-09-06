package com.petrov.databases.entity.credit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(schema = "test")
public class CreditPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)

    private Credit credit;

    @Column(nullable = false)
    private LocalDate paymentDate;

    @Column(nullable = false)
    private boolean wasDone;

    @Column(scale = 2)
    private BigDecimal depositedAmount;
}
