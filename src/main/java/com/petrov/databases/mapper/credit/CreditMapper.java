package com.petrov.databases.mapper.credit;

import com.petrov.databases.dto.credit.CreditDTO;
import com.petrov.databases.entity.credit.Credit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditMapper {
    Credit creditDtoToCredit(CreditDTO creditDTO);
    CreditDTO creditToCreditDto(Credit credit);
}
