package com.petrov.databases.dto.credit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class CreditDTO {
    private Long id;
    private Long debitAccountId;
    private Long refinancedCreditId;
    private Set<Long> creditPaymentsIds;
    private Long pledgeId;
    private BigDecimal initialAmount;
    private BigDecimal debt;
    private LocalDate openedDate;
    private LocalDate closedDate;
    private String rate;
    private boolean hasPenalty = false;

    public void addPenalty() {
        hasPenalty = true;
    }

    public void removePenalty() {
        hasPenalty = false;
    }
    private short term;
}
