package com.petrov.databases.mapper.debitaccount;

import com.petrov.databases.dto.client.ClientDTO;
import com.petrov.databases.dto.client.DebitAccountDTO;
import com.petrov.databases.entity.Client;
import com.petrov.databases.entity.debitaccount.DebitAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DebitAccountMapper {

    DebitAccount debitAccountDtoToDebitAccount(DebitAccountDTO debitAccountDTO);
    DebitAccountDTO debitAccountToDebitAccountDto(DebitAccount debitAccount);
}
