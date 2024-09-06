package com.petrov.databases.service.cardgenerator;

import com.petrov.databases.entity.debitcard.PaymentSystem;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CardNumberGenerator {
    private static final Map<PaymentSystem, List<String>> CARD_PREFIXES = new HashMap<>();

    static {
        CARD_PREFIXES.put(PaymentSystem.VISA, Arrays.asList("4"));
        CARD_PREFIXES.put(PaymentSystem.MASTERCARD, Arrays.asList("51", "52", "53", "54", "55", "2221", "2222", "2223", "2224", "2225", "2226", "2227", "2228", "2229", "223", "224", "225", "226", "227", "228", "229", "23", "24", "25", "26", "270", "271", "2720"));
        CARD_PREFIXES.put(PaymentSystem.MIR, Arrays.asList("2200", "2201", "2202", "2203", "2204"));
    }

    private Random random = new Random();

    public String generateCardNumber(PaymentSystem paymentSystem) {
        List<String> prefixes = CARD_PREFIXES.get(paymentSystem);
        String prefix = prefixes.get(random.nextInt(prefixes.size()));
        StringBuilder cardNumber = new StringBuilder(prefix);
        int length = 16; // Длина номера карты обычно 16 цифр

        while (cardNumber.length() < length - 1) {
            cardNumber.append(random.nextInt(10));
        }

        cardNumber.append(calculateLuhnChecksum(cardNumber.toString()));
        return cardNumber.toString();
    }

    private int calculateLuhnChecksum(String number) {
        int sum = 0;
        boolean alternate = false;
        for (int i = number.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(number.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (10 - (sum % 10)) % 10;
    }
}
