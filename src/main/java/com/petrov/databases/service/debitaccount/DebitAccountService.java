package com.petrov.databases.service.debitaccount;

import com.petrov.databases.entity.Client;
import com.petrov.databases.entity.debitaccount.DebitAccount;
import com.petrov.databases.entity.debitcard.DebitCard;
import com.petrov.databases.exception.DebitAccountAlreadyExistsException;
import com.petrov.databases.repository.DebitAccountRepository;
import com.petrov.databases.service.client.ClientService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DebitAccountService {
    private static final Logger log = LoggerFactory.getLogger(DebitAccountService.class);
    private ClientService clientService;
    private DebitAccountRepository debitAccountRepository;

    @Transactional
    public void saveDebitAccountWithClient(DebitAccount debitAccount, UserDetails userDetails, DebitCard debitCard) {
        Client client = clientService.getClientByEmail(userDetails.getUsername());
        debitAccount.setClient(client);
        client.getDebitAccounts().add(debitAccount);
        clientService.saveClient(client);
        Example<DebitAccount> example = Example.of(debitAccount);
        if (!debitAccountRepository.exists(example))
            debitAccountRepository.save(debitAccount);
        else throw new DebitAccountAlreadyExistsException();
    }

    public DebitAccount getDebitAccount(String accountNumber) {
        DebitAccount debitAccount = debitAccountRepository
                .findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException(STR."DebitAccount with accountNumber= \{ accountNumber } was not found"));
        return debitAccount;

    }

    public DebitAccount getDebitAccount(Long id) {
        DebitAccount debitAccount = debitAccountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(STR."DebitAccount with id = \{ id } was not found"));
        return debitAccount;

    }

    public void save(DebitAccount debitAccount) {
        debitAccountRepository.save(debitAccount);
    }


}
