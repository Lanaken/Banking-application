package com.petrov.databases.mapper.credit;

import com.petrov.databases.dto.credit.CreditDTO;
import com.petrov.databases.entity.credit.Credit;
import com.petrov.databases.repository.CreditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class CreditMapperWrapper {
    private CreditMapper creditMapper;
    private CreditRepository creditRepository;

    public CreditDTO CreditToCreditDto(Credit credit) {
        return creditMapper.creditToCreditDto(credit);
    }
}
