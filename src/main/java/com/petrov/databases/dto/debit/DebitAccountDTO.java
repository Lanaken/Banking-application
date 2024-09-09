package com.petrov.databases.dto.debit;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class DebitAccountDTO {
    private Long id;
    private Long ClientId;
    private Set<Long> debitCardIds;
    private Set<Long> creditIds;
    @Column(nullable = false, scale = 2)
    private BigDecimal currentAmount = BigDecimal.ZERO;
    @Column(nullable = false)
    private boolean isActive = true;
    public void enableAccount() {
        isActive = true;
    }

    public void disableAccount() {
        isActive = false;
    }

    @Column(nullable = false, unique = true, updatable = false, length = 16)
    private String accountNumber;
}
