package com.petrov.databases.entity.pledge;

import com.petrov.databases.entity.credit.Credit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
@AllArgsConstructor
@Table(schema = "test")
public class Pledge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false, mappedBy = "pledge")
    private Credit credit;

    @Column(nullable = false)
    private PledgeType pledgeType;

    @Column(nullable = false, updatable = false, scale = 2)
    private BigDecimal cost;
}
