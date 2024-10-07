package com.petrov.databases.service.credit;

import com.petrov.databases.dto.credit.CreditDTO;
import com.petrov.databases.dto.credit.PledgeDto;
import com.petrov.databases.entity.credit.Credit;
import com.petrov.databases.entity.credit.CreditPayment;
import com.petrov.databases.entity.debitaccount.DebitAccount;
import com.petrov.databases.entity.pledge.Pledge;
import com.petrov.databases.mapper.credit.CreditMapper;
import com.petrov.databases.mapper.credit.PledgeMapper;
import com.petrov.databases.repository.CreditRepository;
import com.petrov.databases.service.debitaccount.DebitAccountService;
import com.petrov.databases.service.parametergenerator.CreditParametersGenerator;
import com.petrov.databases.service.pledge.PledgeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class CreditService {
    private static final Logger log = LoggerFactory.getLogger(CreditService.class);
    private CreditRepository creditRepository;
    private DebitAccountService debitAccountService;
    private CreditMapper creditMapper;
    private CreditParametersGenerator creditParametersGenerator;
    private PledgeMapper pledgeMapper;
    private Clock clock;


    @Transactional
    public Credit openCredit(Long accountId, CreditDTO creditDTO) {
        log.info("Start openCredit");
        Credit credit = creditParametersGenerator.generateCreditParameters(creditDTO);
        DebitAccount debitAccount = debitAccountService.getDebitAccount(accountId);
        debitAccount.addCredit(credit);
        debitAccountService.save(debitAccount);
        log.info("Finish openCredit");
        return credit;
    }

    @Transactional
    public Credit openCredit(Long accountId, CreditDTO creditDTO, List<String> refinancedCreditIds) {
        List<Credit> credits = refinancedCreditIds.stream().map(id -> creditRepository.findByUuid(id)).toList();
        if (!checkIfAmountIsEnough(creditDTO, credits))
            throw new RuntimeException("Refinancing amount is not enough");
        Credit credit = creditParametersGenerator.generateCreditParameters(creditDTO);
        DebitAccount debitAccount = debitAccountService.getDebitAccount(accountId);
        debitAccount.addCredit(credit);
        credits.forEach(credit1 -> debitAccount.decreaseCurrentAmount(credit1.getDebt()));
        credits.forEach(refinancedCredit -> refinancedCredit.setClosedDate(LocalDate.now(clock)));
        credits.forEach(refinancedCredit -> refinancedCredit.setDebt(BigDecimal.ZERO));
        //    credits.forEach(refinancedCredit -> refinancedCredit.getCreditPayments().forEach(CreditPayment::makePayment));
        credit.addRefinancingCredits(credits);
        //credits.forEach(refinancedCredits -> creditRepository.save(refinancedCredits));
        debitAccountService.save(debitAccount);
        return credit;
    }

    @Transactional
    public Credit openPledgeCredit(Long accountId, CreditDTO creditDTO, PledgeDto pledgeDto) {
        Credit credit = creditParametersGenerator.generateCreditParameters(creditDTO);
        DebitAccount debitAccount = debitAccountService.getDebitAccount(accountId);
        debitAccount.addCredit(credit);
        Pledge pledge = pledgeMapper.pledgeDtoToPledge(pledgeDto);
        credit.addPledge(pledge);
        debitAccountService.save(debitAccount);
        return credit;
    }

    private boolean checkIfAmountIsEnough(CreditDTO creditDTO, List<Credit> refinancedCredits) {
        return creditDTO.getAmount().max(refinancedCredits.stream().map(Credit::getDebt).reduce(BigDecimal::add).get()).equals(creditDTO.getAmount());
    }

    public Credit getCreditWithPayments(String uuid){
        return creditRepository.findByUuidWithPayments(uuid);
    }
}
