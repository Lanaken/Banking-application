package com.petrov.databases.service.debitaccount;

import com.petrov.databases.dto.debit.DebitCardDto;
import com.petrov.databases.entity.debitcard.PaymentSystem;
import com.petrov.databases.service.parametergenerator.CardNumberGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DebitCardParametersGeneratorTest {

    @InjectMocks
    private DebitCardParametersGenerator debitCardParametersGenerator;

    @Mock
    private CardNumberGenerator cardNumberGenerator;

    @Mock
    private Clock clock;

    private final LocalDate fixedDate = LocalDate.of(2023, 9, 7); // Фиксированная дата для тестов
    private final Clock fixedClock = Clock.fixed(fixedDate.atStartOfDay(ZoneOffset.UTC).toInstant(), ZoneId.systemDefault());

    @BeforeEach
    public void setUp() throws NoSuchAlgorithmException {
        MockitoAnnotations.openMocks(this); // Инициализация Mockito аннотаций
        when(clock.instant()).thenReturn(fixedClock.instant());
        when(clock.getZone()).thenReturn(fixedClock.getZone());
    }

    @Test
    public void testGenerateDebitCardParameters_Visa() throws NoSuchAlgorithmException {
        String expectedCardNumber = "4111111111111111"; // Замокированный номер карты
        when(cardNumberGenerator.generateCardNumber(PaymentSystem.VISA)).thenReturn(expectedCardNumber);

        DebitCardDto debitCardDto = debitCardParametersGenerator.generateDebitCardParameters(PaymentSystem.VISA);

        // Проверка номера карты
        assertEquals(expectedCardNumber, debitCardDto.getCardNumber());
        assertEquals(PaymentSystem.VISA, debitCardDto.getSystem());

        // Проверка срока действия карты (3 года с фиксированной даты)
        assertEquals(fixedDate.plusYears(3), debitCardDto.getExpirationDate());

        // Проверка наличия хешей CVV и PIN-кода
        assertNotNull(debitCardDto.getCvvHash(), "CVV hash should not be null");
        assertNotNull(debitCardDto.getPinCodeHash(), "PIN code hash should not be null");

        assertEquals(32, debitCardDto.getPinCodeHash().length, "PIN code hash should have 32 bytes length");
    }

    @Test
    public void testGenerateDebitCardParameters_MasterCard() throws NoSuchAlgorithmException {
        String expectedCardNumber = "5111111111111111"; // Замокированный номер карты для MasterCard
        when(cardNumberGenerator.generateCardNumber(PaymentSystem.MASTERCARD)).thenReturn(expectedCardNumber);

        DebitCardDto debitCardDto = debitCardParametersGenerator.generateDebitCardParameters(PaymentSystem.MASTERCARD);

        // Проверка номера карты
        assertEquals(expectedCardNumber, debitCardDto.getCardNumber());
        assertEquals(PaymentSystem.MASTERCARD, debitCardDto.getSystem());

        // Проверка срока действия карты (3 года с фиксированной даты)
        assertEquals(fixedDate.plusYears(3), debitCardDto.getExpirationDate());

        // Проверка наличия хешей CVV и PIN-кода
        assertNotNull(debitCardDto.getCvvHash(), "CVV hash should not be null");
        assertNotNull(debitCardDto.getPinCodeHash(), "PIN code hash should not be null");

        // Проверяем длину хешей, SHA-256 выдает 32 байта (256 бит)
        assertEquals(32, debitCardDto.getPinCodeHash().length, "PIN code hash should have 32 bytes length");
    }

    @Test
    public void testGenerateDebitCardParameters_Mir() throws NoSuchAlgorithmException {
        String expectedCardNumber = "2200111111111111"; // Замокированный номер карты для Мир
        when(cardNumberGenerator.generateCardNumber(PaymentSystem.MIR)).thenReturn(expectedCardNumber);

        DebitCardDto debitCardDto = debitCardParametersGenerator.generateDebitCardParameters(PaymentSystem.MIR);

        // Проверка номера карты
        assertEquals(expectedCardNumber, debitCardDto.getCardNumber());
        assertEquals(PaymentSystem.MIR, debitCardDto.getSystem());

        // Проверка срока действия карты (3 года с фиксированной даты)
        assertEquals(fixedDate.plusYears(3), debitCardDto.getExpirationDate());

        // Проверка наличия хешей CVV и PIN-кода
        assertNotNull(debitCardDto.getCvvHash(), "CVV hash should not be null");
        assertNotNull(debitCardDto.getPinCodeHash(), "PIN code hash should not be null");

        // Проверяем длину хешей, SHA-256 выдает 32 байта (256 бит)
        assertEquals(32, debitCardDto.getPinCodeHash().length, "PIN code hash should have 32 bytes length");
    }
}
