package com.petrov.databases.service.credit;

import com.petrov.databases.dto.DebtByMonthDto;
import com.petrov.databases.entity.credit.Credit;
import com.petrov.databases.entity.credit.CreditPayment;
import com.petrov.databases.entity.debitaccount.DebitAccount;
import com.petrov.databases.repository.CreditPaymentRepository;
import com.petrov.databases.repository.CreditRepository;
import com.petrov.databases.service.debitaccount.DebitAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

@Service
@AllArgsConstructor
public class CreditPaymentService {
    private CreditPaymentRepository creditPaymentRepository;
    private CreditRepository creditRepository;
    private DebitAccountService debitAccountService;
    private Clock clock;

    public List<CreditPayment> getCreditPaymentsByCreditId(String uuid) {
        return creditPaymentRepository.findByCreditUuid(uuid);
    }

    @Transactional
    public void closeNearestOpenedPayment(String uuid, Long debitAccountId) {
        CreditPayment payment = creditPaymentRepository.findNearestNotClosedCreditPayment(uuid);
        Credit credit = payment.getCredit();
        DebitAccount debitAccount = debitAccountService.getDebitAccount(debitAccountId);
        if (debitAccount.getCurrentAmount().min(payment.getDepositedAmount()).equals(debitAccount.getCurrentAmount()))
            throw new RuntimeException("Недостаточно денег на счету");
        debitAccount.decreaseCurrentAmount(payment.getDepositedAmount());
        payment.makePayment();
        credit.reduceDebt(payment.getDepositedAmount());
        if (credit.getDebt().equals(BigDecimal.ZERO))
            credit.setClosedDate(LocalDate.now(clock));
        debitAccountService.save(debitAccount);
    }

    public List<DebtByMonthDto> getDebtByMonths(Long clientId) {
        List<Object[]> debtPerMonths = creditPaymentRepository.findDebtByMonths(clientId);
        return debtPerMonths
                .stream()
                .map(objects -> new DebtByMonthDto(
                        objects[1].toString(),
                        objects[2].toString(),
                        new BigDecimal(objects[0].toString())))
                .toList();
    }

    public List<DebtByMonthDto> getDebtByMonthsForAccount(Long accountId) {
        List<Object[]> debtPerMonths = creditPaymentRepository.findDebtByMonthsWithAccountId(accountId);
        return debtPerMonths
                .stream()
                .map(objects -> new DebtByMonthDto(
                        objects[1].toString(),
                        objects[2].toString(),
                        new BigDecimal(objects[0].toString())))
                .toList();
    }
}
