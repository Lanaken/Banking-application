package com.petrov.databases.service.debitaccount;

import com.mifmif.common.regex.Generex;
import com.petrov.databases.dto.client.DebitAccountDTO;
import org.springframework.stereotype.Service;

@Service
public class DebitAccountParametersGenerator {
    private Generex accountNumberGenerator = new Generex("^[0-9]{16}$");

    public DebitAccountDTO generateDebitAccountDtoParameters() {
        DebitAccountDTO debitAccountDTO = DebitAccountDTO
                .builder()
                .accountNumber(accountNumberGenerator.random())
                .build();
        return debitAccountDTO;
    }
}
