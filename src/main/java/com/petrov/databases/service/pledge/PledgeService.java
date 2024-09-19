package com.petrov.databases.service.pledge;

import com.petrov.databases.dto.credit.PledgeDto;
import com.petrov.databases.entity.pledge.Pledge;
import com.petrov.databases.mapper.credit.PledgeMapper;
import com.petrov.databases.repository.PledgeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PledgeService {
    private PledgeRepository pledgeRepository;
    private PledgeMapper pledgeMapper;

    public void savePledge(PledgeDto pledgeDto) {
        Pledge pledge = pledgeMapper.pledgeDtoToPledge(pledgeDto);
        pledgeRepository.save(pledge);
    }

    public Pledge getPledge(Long id) {
        return pledgeRepository.getReferenceById(id);
    }

}
