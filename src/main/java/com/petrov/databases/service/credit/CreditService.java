package com.petrov.databases.service.credit;

import com.petrov.databases.dto.credit.CreditDTO;
import com.petrov.databases.entity.credit.Credit;
import com.petrov.databases.entity.debitaccount.DebitAccount;
import com.petrov.databases.mapper.credit.CreditMapper;
import com.petrov.databases.repository.CreditRepository;
import com.petrov.databases.repository.DebitAccountRepository;
import com.petrov.databases.service.debitaccount.DebitAccountService;
import com.petrov.databases.service.parametergenerator.CreditParametersGenerator;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CreditService {
    private static final Logger log = LoggerFactory.getLogger(CreditService.class);
    private CreditRepository creditRepository;
    private DebitAccountService debitAccountService;
    private CreditMapper creditMapper;
    private CreditParametersGenerator creditParametersGenerator;

    @Transactional
    public Credit openCredit(String accountNumber, CreditDTO creditDTO) {
        log.info("Start openCredit");
        Credit credit = creditParametersGenerator.generateCreditParameters(creditDTO);
        DebitAccount debitAccount = debitAccountService.getDebitAccount(accountNumber);
        debitAccount.addCredit(credit);
        debitAccountService.save(debitAccount);
        log.info("Finish openCredit");
        return credit;
    }
}
