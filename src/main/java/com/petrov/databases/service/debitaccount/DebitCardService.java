package com.petrov.databases.service.debitaccount;

import com.petrov.databases.dto.debit.DebitCardDto;
import com.petrov.databases.entity.debitaccount.DebitAccount;
import com.petrov.databases.entity.debitcard.DebitCard;
import com.petrov.databases.entity.debitcard.PaymentSystem;
import com.petrov.databases.mapper.debitaccount.DebitCardMapper;
import com.petrov.databases.repository.ClientRepository;
import com.petrov.databases.repository.DebitAccountRepository;
import com.petrov.databases.repository.DebitCardRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DebitCardService {
    private static final Logger log = LoggerFactory.getLogger(DebitAccountService.class);
    private DebitAccountRepository debitAccountRepository;
    private DebitCardRepository debitCardRepository;
    private DebitCardMapper debitCardMapper;

//    @Transactional
//    public void save(DebitAccount debitAccount, DebitCardDto debitCardDto) {
//        log.info("Start adding Debit card");
//        DebitCard debitCard = debitCardMapper.debitCardDtoToDebitCard(debitCardDto);
//        debitCard
//
//    }
}