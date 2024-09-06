package com.petrov.databases.service.debitaccount;

import com.petrov.databases.dto.debit.DebitCardDto;
import com.petrov.databases.entity.debitcard.PaymentSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DebitCardParametersGeneratorTest {

    private DebitCardParametersGenerator generator;

    @BeforeEach
    void setUp() throws Exception {
        generator = new DebitCardParametersGenerator();
    }

    @Test
    void testGenerateDebitCardParameters() {
        for (PaymentSystem system : PaymentSystem.values()) {
            DebitCardDto debitCardDto = generator.generateDebitCardParameters(system);

            assertNotNull(debitCardDto.getCardNumber(), "Card number should not be null");
            assertNotNull(debitCardDto.getCvvHash(), "CVV hash should not be null");
            assertNotNull(debitCardDto.getPinCodeHash(), "PIN code hash should not be null");
            assertEquals(system, debitCardDto.getSystem(), "Payment system should match the input");

            // Дополнительные проверки
            assertEquals(16, debitCardDto.getCardNumber().length(), "Card number length should be 16");
            assertEquals(32, debitCardDto.getCvvHash().length, "CVV hash length should be 32 bytes (256 bits)");
            assertEquals(32, debitCardDto.getPinCodeHash().length, "PIN code hash length should be 32 bytes (256 bits)");
        }
    }
}