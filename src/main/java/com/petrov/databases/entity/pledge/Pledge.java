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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "test")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pledge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne(optional = false, mappedBy = "pledge")
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private PledgeType pledgeType;

    @Column(nullable = false, updatable = false, scale = 2)
    @EqualsAndHashCode.Include
    private BigDecimal cost;
}
