package com.petrov.databases.entity.debitaccount;

import com.petrov.databases.entity.Client;
import com.petrov.databases.entity.credit.Credit;
import com.petrov.databases.entity.debitcard.DebitCard;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Table(schema = "test")
public class DebitAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Setter
    private Client client;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "debitAccount")
    private Set<DebitCard> debitCards;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "debitAccount")
    private Set<Credit> credits;

    @Setter
    @Column(nullable = false, scale = 2)
    private BigDecimal currentAmount = BigDecimal.ZERO;

    @Column(nullable = false)
    private boolean isActive = false;

    @Column(nullable = false, unique = true, updatable = false, length = 16)
    private String accountNumber;
}
