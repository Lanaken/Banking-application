package com.petrov.databases.mapper.debitaccount;

import com.petrov.databases.dto.debit.DebitAccountDTO;
import com.petrov.databases.entity.debitaccount.DebitAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DebitAccountMapper {

    DebitAccount debitAccountDtoToDebitAccount(DebitAccountDTO debitAccountDTO);
    DebitAccountDTO debitAccountToDebitAccountDto(DebitAccount debitAccount);
}
