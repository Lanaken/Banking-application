package com.petrov.databases.dto.credit;

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
    private Long id;
    private Long debitAccountId;
    private Long refinancedCreditId;
    private Set<Long> creditPaymentsIds;
    private Long pledgeId;
    @NotNull(message = "Amount is required")
    @Min(value = 50000, message = "Amount must be at least 1000")
    private BigDecimal amount;
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
