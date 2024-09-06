package com.petrov.databases.service.cardgenerator;

import com.petrov.databases.entity.debitcard.PaymentSystem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardNumberGeneratorTest {

    private final CardNumberGenerator generator = new CardNumberGenerator();

    @Test
    public void testVisaCardNumber() {
        String cardNumber = generator.generateCardNumber(PaymentSystem.VISA);
        assertTrue(cardNumber.startsWith("4"));
        assertEquals(16, cardNumber.length());
        assertTrue(isValidCardNumber(cardNumber));
    }

    @Test
    public void testMasterCardNumber() {
        String cardNumber = generator.generateCardNumber(PaymentSystem.MASTERCARD);
        assertTrue(cardNumber.startsWith("5") || cardNumber.matches("^22[2-7][1-9]"));
        assertEquals(16, cardNumber.length());
        assertTrue(isValidCardNumber(cardNumber));
    }

    @Test
    public void testMirCardNumber() {
        String cardNumber = generator.generateCardNumber(PaymentSystem.MIR);
        assertTrue(cardNumber.startsWith("220"));
        assertEquals(16, cardNumber.length());
        assertTrue(isValidCardNumber(cardNumber));
    }

    // Вспомогательный метод для проверки валидности номера карты по алгоритму Луна
    private boolean isValidCardNumber(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
}
