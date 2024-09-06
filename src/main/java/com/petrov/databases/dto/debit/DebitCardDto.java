package com.petrov.databases.dto.debit;

import com.petrov.databases.entity.debitcard.PaymentSystem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class DebitCardDto {
    private Long id;
    private Long debitAccountId;
    private boolean isActive = true;
    private String cardNumber;
    private PaymentSystem system;
    private byte[] cvvHash;
    private byte[] pinCodeHash;
    private LocalDate expirationDate;
    public void disable() {
        isActive = false;
    }
}

