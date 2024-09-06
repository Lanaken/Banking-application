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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "test")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DebitCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private DebitAccount debitAccount;

    @Column(nullable = false)
    private boolean isActive = false;

    @Column(nullable = false, unique = true, updatable = false, length = 16)
    private String cardNumber;

    @Column(nullable = false, updatable = false)
    private PaymentSystem system;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false, updatable = false)
    private byte[] cvvHash;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private byte[] pinCodeHash;
}
