package com.petrov.databases.dto.credit;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
public class CreditDTO {
    private String id;
    private Long debitAccountId;
    private String refinancingCreditId;
    private Set<Long> creditPaymentsIds;
    private Long pledgeId;
    @NotNull(message = "Введите сумму")
    @Min(value = 50000, message = "Размер кредита должен быть не меньше 50000")
    private BigDecimal amount;
    private BigDecimal debt;
    private LocalDate openedDate;
    private LocalDate closedDate;
    private BigDecimal rate;
    private boolean hasPenalty = false;

    public void addPenalty() {
        hasPenalty = true;
    }

    public void removePenalty() {
        hasPenalty = false;
    }

    @NotNull(message = "Term is required")
    @Min(value = 6, message = "Term must be at least 6 months")
    private short term;
}
