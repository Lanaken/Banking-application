package com.petrov.databases.entity.debitcard;

import com.petrov.databases.entity.debitaccount.DebitAccount;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(schema = "test")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DebitCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private DebitAccount debitAccount;

    @Column(nullable = false)
    private boolean isActive = false;

    @Column(nullable = false, unique = true, updatable = false, length = 16)
    @EqualsAndHashCode.Include
    private String cardNumber;

    @Column(nullable = false, updatable = false)
    private PaymentSystem system;

    @Column(nullable = false, updatable = false)
    private String cvvHash;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private byte[] pinCodeHash;

    private LocalDate expirationDate;
}
