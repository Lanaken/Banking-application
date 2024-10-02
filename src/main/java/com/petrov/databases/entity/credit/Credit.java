package com.petrov.databases.entity.credit;

import com.petrov.databases.entity.debitaccount.DebitAccount;
import com.petrov.databases.entity.pledge.Pledge;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "test")
public class Credit {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    private String uuid;

    @ManyToOne(optional = false)
    @JoinColumn(name = "debit_account_id")
    private DebitAccount debitAccount;

    @ManyToOne
    @JoinColumn(name = "credit_id")
    private Credit refinancingCredits;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private Set<CreditPayment> creditPayments;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "pledge_id")
    private Pledge pledge;

    @Column(nullable = false, updatable = false, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, scale = 2)
    private BigDecimal debt;

    @Column(nullable = false)
    private LocalDate openedDate;

    @Column
    private LocalDate closedDate;

    @Column(nullable = false)
    private BigDecimal rate;

    @Column(nullable = false)
    private boolean hasPenalty = false;

    @Column(nullable = false, updatable = false)
    private short term;

    public void addCreditPayments(Set<CreditPayment> creditPayments) {
        creditPayments.forEach(creditPayment -> creditPayment.setCredit(this));
        this.setCreditPayments(creditPayments);
    }

    public void addPledge(Pledge pledge) {
        pledge.setCredit(this);
        this.setPledge(pledge);
    }

    public void addRefinancingCredits(List<Credit> refinancingCredits) {
        refinancingCredits
                .forEach(refinancingCredit -> refinancingCredit.setRefinancingCredits(this));
    }

    public void reduceDebt(BigDecimal debt) {
        this.debt = this.debt.subtract(debt);
    }
}
