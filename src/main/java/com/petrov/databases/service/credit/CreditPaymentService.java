package com.petrov.databases.service.credit;

import com.petrov.databases.entity.credit.CreditPayment;
import com.petrov.databases.repository.CreditPaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CreditPaymentService {
    private CreditPaymentRepository creditPaymentRepository;

    public List<CreditPayment> getCreditPaymentsByCreditId(String uuid){
        return creditPaymentRepository.findByCreditUuid(uuid);
    }
}
