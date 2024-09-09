package com.petrov.databases.dto.debit;

import com.petrov.databases.entity.debitcard.PaymentSystem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter // Добавляем сеттеры для всех полей
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DebitCardDto {
    private Long id;
    private Long debitAccountId;
    private Boolean isActive = true; // Используем объект Boolean вместо примитива
    public String cardNumber;
    private PaymentSystem system;
    private String cvvHash;
    private byte[] pinCodeHash;
    private LocalDate expirationDate;

    public void disable() {
        isActive = false;
    }
}
