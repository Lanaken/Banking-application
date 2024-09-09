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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Lombok будет генерировать только то, что явно указано
@Table(schema = "test")
public class DebitAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Используем только id для генерации equals и hashCode

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Client client;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "debitAccount")
    private Set<DebitCard> debitCards = new HashSet<>();

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "debitAccount")
    private Set<Credit> credits = new HashSet<>();

    @Column(nullable = false, scale = 2)
    private BigDecimal currentAmount = BigDecimal.ZERO;

    @Column(nullable = false)
    private boolean isActive;

    @EqualsAndHashCode.Include
    @Column(nullable = false, unique = true, updatable = false, length = 16)
    private String accountNumber;

    public void addDebitCard(DebitCard debitCard) {
        this.debitCards.add(debitCard);
        debitCard.setDebitAccount(this);
    }

    public void addCredit(Credit credit) {
        credits.add(credit);
        this.setCurrentAmount(currentAmount.add(credit.getAmount()));
        credit.setDebitAccount(this);
    }
}