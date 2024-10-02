package com.petrov.databases.service.credit;

import com.petrov.databases.entity.credit.Credit;
import com.petrov.databases.entity.credit.CreditPayment;
import com.petrov.databases.entity.debitaccount.DebitAccount;
import com.petrov.databases.repository.CreditPaymentRepository;
import com.petrov.databases.repository.CreditRepository;
import com.petrov.databases.service.debitaccount.DebitAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CreditPaymentService {
    private CreditPaymentRepository creditPaymentRepository;
    private CreditRepository creditRepository;
    private DebitAccountService debitAccountService;

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
        debitAccountService.save(debitAccount);
    }
}
