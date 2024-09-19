package com.petrov.databases.dto.credit;

import com.petrov.databases.entity.pledge.PledgeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class PledgeDto {
    private Long id;
    private String creditId;
    private PledgeType pledgeType;
    private BigDecimal cost;
}

