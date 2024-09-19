package com.petrov.databases.mapper.credit;

import com.petrov.databases.dto.credit.CreditDTO;
import com.petrov.databases.entity.credit.Credit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreditMapper {
    @Mapping(target = "uuid", source = "id")
    Credit creditDtoToCredit(CreditDTO creditDTO);

    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "refinancingCreditId", source = "refinancingCredits.uuid")
    @Mapping(target = "pledgeId", source = "pledge.id")
    CreditDTO creditToCreditDto(Credit credit);
}
