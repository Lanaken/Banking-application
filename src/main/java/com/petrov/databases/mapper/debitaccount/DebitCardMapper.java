package com.petrov.databases.mapper.debitaccount;

import com.petrov.databases.dto.debit.DebitCardDto;
import com.petrov.databases.encryption.EncryptionUtil;
import com.petrov.databases.entity.debitcard.DebitCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        imports = EncryptionUtil.class
)
public interface DebitCardMapper {
    DebitCard debitCardDtoToDebitCard(DebitCardDto debitCardDto);
    @Mapping(target = "cvvHash", expression = "java(EncryptionUtil.decryptCVV(debitCard.getCvvHash()))")
    DebitCardDto debitCardToDebitCardDto(DebitCard debitCard);
}
