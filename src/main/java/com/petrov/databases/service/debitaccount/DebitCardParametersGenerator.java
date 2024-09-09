package com.petrov.databases.service.debitaccount;

import com.mifmif.common.regex.Generex;
import com.petrov.databases.dto.debit.DebitCardDto;
import com.petrov.databases.encryption.EncryptionUtil;
import com.petrov.databases.entity.debitcard.PaymentSystem;
import com.petrov.databases.service.parametergenerator.CardNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.time.LocalDate;

@Service

public class DebitCardParametersGenerator {
    //private Generex cardNumberGenerator = new Generex("^[0-9]{16}$");
    @Autowired
    private CardNumberGenerator cardNumberGenerator;
    private Generex cvvGenerator = new Generex("[0-9]{3}");
    private Generex pinCodeGenerator = new Generex("^[0-9]{4}$");
    private MessageDigest digest = MessageDigest.getInstance("SHA-256");
    @Autowired
    private Clock clock;

    public DebitCardParametersGenerator() throws NoSuchAlgorithmException {}


    public DebitCardDto generateDebitCardParameters(PaymentSystem paymentSystem) {
        String cardNumber = cardNumberGenerator.generateCardNumber(paymentSystem);
        String cvvHash = EncryptionUtil.encryptCVV(cvvGenerator.random());
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
