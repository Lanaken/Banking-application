package com.petrov.databases.service;

import com.petrov.databases.dto.client.ClientDTO;
import com.petrov.databases.dto.client.DebitAccountDTO;
import com.petrov.databases.dto.debit.DebitCardDto;
import com.petrov.databases.entity.Client;
import com.petrov.databases.entity.debitaccount.DebitAccount;
import com.petrov.databases.entity.debitcard.DebitCard;
import com.petrov.databases.entity.debitcard.PaymentSystem;
import com.petrov.databases.mapper.debitaccount.DebitAccountMapper;
import com.petrov.databases.mapper.debitaccount.DebitCardMapper;
import com.petrov.databases.repository.ClientRepository;
import com.petrov.databases.repository.DebitAccountRepository;
import com.petrov.databases.service.client.ClientService;
import com.petrov.databases.service.debitaccount.DebitAccountParametersGenerator;
import com.petrov.databases.service.debitaccount.DebitAccountService;
import com.petrov.databases.service.debitaccount.DebitCardParametersGenerator;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class BankingService {
    private static final Logger log = LoggerFactory.getLogger(BankingService.class);
    private ClientService clientService;
    private DebitAccountService debitAccountService;
    private DebitCardMapper debitCardMapper;
    private DebitAccountMapper debitAccountMapper;
    private DebitCardParametersGenerator debitCardParametersGenerator;
    private DebitAccountParametersGenerator debitAccountParametersGenerator;

    public void openDebitCardWithNewCreditAccountNumber(PaymentSystem paymentSystem, ClientDTO clientDTO) {
        log.info("Start openDebitCardWithNewCreditAccountNumber");
        Client client = clientService.getClientByEmail(clientDTO.getEmail());
        DebitCardDto debitCardDto = debitCardParametersGenerator.generateDebitCardParameters(paymentSystem);
        DebitCard debitCard = debitCardMapper.debitCardDtoToDebitCard(debitCardDto);
        DebitAccountDTO debitAccountDTO = debitAccountParametersGenerator.generateDebitAccountDtoParameters();
        DebitAccount debitAccount = debitAccountMapper.debitAccountDtoToDebitAccount(debitAccountDTO);
        debitAccount.getDebitCards().add(debitCard);
        client.getDebitAccount().add(debitAccount);
        clientService.saveClient(client);
        log.info("Finish openDebitCardWithNewCreditAccountNumber");
    }

    public void openDebitCard(PaymentSystem paymentSystem, String accountNumber) {
        log.info("Start openDebitCard");
        DebitCardDto debitCardDto = debitCardParametersGenerator.generateDebitCardParameters(paymentSystem);
        DebitCard debitCard = debitCardMapper.debitCardDtoToDebitCard(debitCardDto);
        DebitAccount debitAccount = debitAccountService.getDebitAccount(accountNumber);
        debitAccount.getDebitCards().add(debitCard);
        debitAccountService.save(debitAccount);
        log.info("Finish openDebitCard");
    }

}
