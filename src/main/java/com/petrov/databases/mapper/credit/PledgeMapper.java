package com.petrov.databases.mapper.credit;

import com.petrov.databases.dto.credit.PledgeDto;
import com.petrov.databases.entity.pledge.Pledge;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PledgeMapper {
    Pledge pledgeDtoToPledge(PledgeDto pledgeDto);
    PledgeDto pledgeToPledgeDto(Pledge pledge);
}
