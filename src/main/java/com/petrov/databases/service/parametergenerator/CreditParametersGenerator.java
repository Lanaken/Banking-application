package com.petrov.databases.service.parametergenerator;

import com.petrov.databases.dto.credit.CreditDTO;
import com.petrov.databases.entity.credit.Credit;
import com.petrov.databases.entity.credit.CreditPayment;
import com.petrov.databases.mapper.credit.CreditMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class CreditParametersGenerator {
    private CreditMapper creditMapper;
    private Clock clock;

    @Transactional
    public Credit generateCreditParameters(CreditDTO creditDTO) {
        Credit credit = creditMapper.creditDtoToCredit(creditDTO);
        BigDecimal rate = generateRate(20.0,30.0);
        creditDTO.setRate(rate);
        Set<CreditPayment> creditPayments = generateCreditPayments(creditDTO);
        credit.setRate(rate);
        credit.setDebt(calculateDebt(creditPayments));
        credit.addCreditPayments(creditPayments);
        credit.setOpenedDate(LocalDate.now(clock));
        return credit;
    }

    protected BigDecimal generateRate(double min, double max) {
        Random random = new Random();
        double randomValue = min + (max - min) * random.nextDouble();
        // Преобразуем в BigDecimal и округляем до 2 знаков после запятой
        return BigDecimal.valueOf(randomValue).setScale(2, RoundingMode.HALF_UP);
    }

    protected Set<CreditPayment> generateCreditPayments(CreditDTO creditDTO) {


        Set<CreditPayment> creditPayments = new HashSet<>();
        BigDecimal ratePerMonth = creditDTO.getRate().divide(new BigDecimal(12), 2, RoundingMode.DOWN);
        BigDecimal creditPaymentSum = creditDTO.getAmount()
                .divide(BigDecimal.valueOf(creditDTO.getTerm()), 2, RoundingMode.HALF_UP)
                .add(creditDTO.getAmount().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
                        .multiply(ratePerMonth)).setScale(2, RoundingMode.HALF_UP);
        for (int i = 1; i < creditDTO.getTerm() + 1; i++)
            creditPayments.add(
                    CreditPayment
                            .builder()
                            .paymentDate(LocalDate.now(clock).plusMonths(i))
                            .depositedAmount(creditPaymentSum)
                            .build());
        return creditPayments;
    }

    private BigDecimal calculateAnnuityPayment(CreditDTO creditDTO) {
        BigDecimal monthlyRate = creditDTO.getRate().divide(BigDecimal.valueOf(12).multiply(BigDecimal.valueOf(100)), 2, RoundingMode.HALF_UP);
        BigDecimal numerator = monthlyRate.multiply(monthlyRate.add(BigDecimal.ONE).pow(creditDTO.getTerm()));
        BigDecimal divider = monthlyRate.add(BigDecimal.ONE).pow(creditDTO.getTerm()).subtract(BigDecimal.ONE);
        return creditDTO.getAmount().multiply(numerator.divide(divider, 2, RoundingMode.HALF_UP));
    }

    private List<BigDecimal> calculateDifferencialPayment(CreditDTO creditDTO) {
        BigDecimal mainDebtPayment = creditDTO.getAmount().divide(BigDecimal.valueOf(creditDTO.getTerm()), 2, RoundingMode.HALF_UP);
        BigDecimal monthlyRate = creditDTO.getRate().divide(BigDecimal.valueOf(12).multiply(BigDecimal.valueOf(100)), 2, RoundingMode.HALF_UP);
        List<BigDecimal> paymentsAmount = new ArrayList<>(creditDTO.getTerm());
        for (int i = 0; i < creditDTO.getTerm(); i++) {
            BigDecimal monthPayment = creditDTO.getAmount().subtract(BigDecimal.valueOf(i).multiply(mainDebtPayment)).multiply(monthlyRate);
            paymentsAmount.add(monthPayment);
        }
        return paymentsAmount;
    }

    private BigDecimal calculateDebt(Set<CreditPayment> payments) {
        return payments.stream().map(CreditPayment::getDepositedAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
