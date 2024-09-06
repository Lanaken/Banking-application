package com.petrov.databases.service.debitaccount;

import com.mifmif.common.regex.Generex;
import com.petrov.databases.dto.debit.DebitCardDto;
import com.petrov.databases.entity.debitaccount.DebitAccount;
import com.petrov.databases.entity.debitcard.DebitCard;
import com.petrov.databases.entity.debitcard.PaymentSystem;
import com.petrov.databases.service.cardgenerator.CardNumberGenerator;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.time.LocalDate;

@Service
public class DebitCardParametersGenerator {
    //private Generex cardNumberGenerator = new Generex("^[0-9]{16}$");
    private CardNumberGenerator cardNumberGenerator;
    private Generex cvvGenerator = new Generex("^[0-9]{3}$");
    private Generex pinCodeGenerator = new Generex("^[0-9]{4}$");
    private MessageDigest digest = MessageDigest.getInstance("SHA-256");
    private Clock clock;

    public DebitCardParametersGenerator() throws NoSuchAlgorithmException {}


    public DebitCardDto generateDebitCardParameters(PaymentSystem paymentSystem) {
        String cardNumber = cardNumberGenerator.generateCardNumber(paymentSystem);
        byte[] cvvHash = digest.digest(cvvGenerator.random().getBytes(StandardCharsets.UTF_8));
        byte[] pinCodeHash = digest.digest(pinCodeGenerator.random().getBytes(StandardCharsets.UTF_8));
        DebitCardDto debitCardDto = DebitCardDto.builder()
                .cardNumber(cardNumber)
                .cvvHash(cvvHash)
                .pinCodeHash(pinCodeHash)
                .system(paymentSystem)
                .expirationDate(LocalDate.now(clock).plusYears(3))
                .build();
        return debitCardDto;
    }
}
