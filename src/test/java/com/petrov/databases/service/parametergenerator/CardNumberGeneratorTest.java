package com.petrov.databases.service.parametergenerator;

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
        System.out.println("Generated MasterCard number: " + cardNumber);
        assertTrue(cardNumber.startsWith("5") || cardNumber.matches("^22[2-9]\\d{2}|^2[3-6]\\d{2}|^27[0-1]\\d|^2720"),
                "MasterCard should start with '5' or be in the range 2221–2720");
        assertEquals(16, cardNumber.length(), "MasterCard number should have 16 digits");
        assertTrue(isValidCardNumber(cardNumber), "MasterCard number should be valid according to Luhn algorithm");
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
