package com.petrov.databases.mapper.debitaccount;

import com.petrov.databases.dto.debit.DebitCardDto;
import com.petrov.databases.entity.debitcard.DebitCard;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DebitCardMapper {
    DebitCard debitCardDtoToDebitCard(DebitCardDto debitCardDto);
    DebitCardDto debitCardToDebitCardDto(DebitCard debitCard);
}
