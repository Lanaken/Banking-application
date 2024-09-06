package com.petrov.databases.dto.debit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@AllArgsConstructor
public class DebitAccountDto {
    private Long id;
    private Long clientId;
    private BigDecimal currentAmount;
    private boolean isActive = true;
    private String accountNumber;
    private Set<Long> debitCardIds;
    private Set<Long> creditIds;

    public void disable(){
        isActive = false;
    }
}
