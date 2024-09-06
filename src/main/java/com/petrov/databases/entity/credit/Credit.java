package com.petrov.databases.entity.credit;

import com.petrov.databases.entity.debitaccount.DebitAccount;
import com.petrov.databases.entity.pledge.Pledge;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@Table(schema = "test")
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "debit_account_id")
    private DebitAccount debitAccount;

    @ManyToOne
    @JoinColumn(name = "credit_id")
    private Credit refinancedCredits;

    @OneToMany(mappedBy = "id")
    private Set<CreditPayment> creditPayments;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pledge_id")
    private Pledge pledge;

    @Column(nullable = false, updatable = false, scale = 2)
    private BigDecimal initialAmount;

    @Column(nullable = false, scale = 2)
    private BigDecimal debt;

    @Column(nullable = false)
    private LocalDate openedDate;

    @Column
    private LocalDate closedDate;

    @Column(nullable = false)
    private String rate;

    @Column(nullable = false)
    private boolean hasPenalty = false;

    public void addPenalty() {
        hasPenalty = true;
    }

    public void removePenalty() {
        hasPenalty = false;
    }

    @Column(nullable = false, updatable = false)
    private short term;
}