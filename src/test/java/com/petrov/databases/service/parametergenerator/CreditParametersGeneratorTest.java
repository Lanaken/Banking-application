package com.petrov.databases.service.parametergenerator;

import com.petrov.databases.dto.credit.CreditDTO;
import com.petrov.databases.entity.credit.Credit;
import com.petrov.databases.entity.credit.CreditPayment;
import com.petrov.databases.mapper.credit.CreditMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CreditParametersGeneratorTest {

    @Mock
    private CreditMapper creditMapper;

    @Mock
    private Clock clock;

    @InjectMocks
    private CreditParametersGenerator creditParametersGenerator;

    private CreditDTO creditDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Пример CreditDTO с тестовыми данными
        creditDTO = new CreditDTO();
        creditDTO.setAmount(BigDecimal.valueOf(10000)); // Сумма кредита
        creditDTO.setRate(BigDecimal.valueOf(25.0));    // Процентная ставка
        creditDTO.setTerm((short) 12);                          // Срок кредита в месяцах

        // Настраиваем clock для возврата фиксированной даты
        Clock fixedClock = Clock.fixed(Instant.parse("2024-01-01T00:00:00Z"), ZoneId.systemDefault());
        when(clock.instant()).thenReturn(fixedClock.instant());
        when(clock.getZone()).thenReturn(fixedClock.getZone());
    }

    @Test
    public void testGenerateRate() {
        BigDecimal rate = creditParametersGenerator.generateRate(20.0, 30.0);
        assertNotNull(rate);
        assertTrue(rate.compareTo(BigDecimal.valueOf(20.0)) >= 0 && rate.compareTo(BigDecimal.valueOf(30.0)) <= 0,
                "Rate should be between 20.0 and 30.0");
    }

    @Test
    public void testGenerateCreditPayments() {
        // Генерация кредитных платежей
        Set<CreditPayment> payments = creditParametersGenerator.generateCreditPayments(creditDTO);

        // Проверка количества платежей
        assertEquals(12, payments.size(), "The number of credit payments should match the term (12 months).");

        // Проверка дат платежей
        LocalDate expectedDate = LocalDate.now(clock).plusMonths(1);
        for (CreditPayment payment : payments) {
            assertTrue(payment.getPaymentDate().isAfter(LocalDate.now(clock)), "Payment date should be after the current date.");
            assertNotNull(payment.getDepositedAmount(), "Deposited amount should not be null.");
        }
    }

    @Test
    public void testGenerateCreditParameters() {
        Credit credit = new Credit();
        when(creditMapper.creditDtoToCredit(creditDTO)).thenReturn(credit);

        // Генерация параметров кредита
        Credit generatedCredit = creditParametersGenerator.generateCreditParameters(creditDTO);

        assertNotNull(generatedCredit);
        assertNotNull(generatedCredit.getOpenedDate());
        assertNotNull(generatedCredit.getRate());
        assertNotNull(generatedCredit.getCreditPayments());

        // Проверяем, что дата открытия кредита — это текущая дата
        assertEquals(LocalDate.now(clock), generatedCredit.getOpenedDate());

        // Проверяем, что кредит содержит сгенерированные платежи
        assertEquals(12, generatedCredit.getCreditPayments().size(), "The credit should contain 12 payments.");
    }
}
