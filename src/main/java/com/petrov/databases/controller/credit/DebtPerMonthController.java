package com.petrov.databases.controller.credit;

import com.petrov.databases.service.credit.CreditPaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@AllArgsConstructor
public class DebtPerMonthController {
    private CreditPaymentService creditPaymentService;

//    @GetMapping("/debt-per-month")
//    public String returnDebtByMonthForAllAccounts() {
//
//    }
}
